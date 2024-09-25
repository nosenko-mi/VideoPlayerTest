package com.nmi.videotest.presentation.video_player

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.navigation.toRoute
import com.nmi.videotest.VideoPlayerNavScreen
import com.nmi.videotest.common.result.Result
import com.nmi.videotest.domain.video_repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoPlayerViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: VideoRepository,
    val player: Player
) : ViewModel() {

    private val args = savedStateHandle.toRoute<VideoPlayerNavScreen>()
    var state by mutableStateOf(VideoPlayerScreenState())
        private set

    val listener =
        object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                state = state.copy(isPlaying = isPlaying)
            }
        }

    init {
        player.addListener(listener)
        player.prepare()
        initLoad()
    }

    fun onAction(action: VideoPlayerAction) {
        when (action) {
            is VideoPlayerAction.NextVideo -> {
                loadNextVideo()
            }

            is VideoPlayerAction.PreviousVideo -> {
                loadPreviousVideo()
            }

            is VideoPlayerAction.StartPause -> {
                if (state.isPlaying) {
                    pause()
                } else {
                    play()
                }
            }

            is VideoPlayerAction.SeekChange -> {
                player.seekTo(action.timeMs.toLong())
            }

            else -> {}
        }
    }

    fun loadNextVideo() {
        viewModelScope.launch(Dispatchers.IO) {
            if (state.currentVideoIndex >= state.videoList.lastIndex) {
                // Should load more with repository, however not for this test app.
                // In this case simply start from the beginning
                state = state.copy(
                    isLoading = true,
                    currentVideo = null,
                    currentVideoIndex = 0,
                )
            } else {
                state = state.copy(
                    isLoading = true,
                    currentVideo = null,
                    currentVideoIndex = state.currentVideoIndex + 1
                )
            }
            loadCurrentVideo()
        }
    }

    fun loadPreviousVideo() {
        viewModelScope.launch(Dispatchers.IO) {
            if (state.currentVideoIndex <= 0) {
                // Do nothing.
            } else {
                state = state.copy(
                    isLoading = true,
                    currentVideo = null,
                    currentVideoIndex = state.currentVideoIndex - 1
                )
                loadCurrentVideo()
            }
        }
    }

    fun setMediaItem(uri: String) {
        state = state.copy(isPlaying = false)
        player.pause()
        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)
    }

    fun play() {
        state = state.copy(
            isPlaying = true
        )
        player.play()
    }

    fun pause() {
        state = state.copy(
            isPlaying = false
        )
        player.pause()
    }


    private fun initLoad() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                isLoading = true,
                currentVideo = null,
                videoList = args.videoIds,
                currentVideoIndex = args.position
            )
            loadCurrentVideo()
        }
    }

    suspend fun loadCurrentVideo() {
        try {
            delay(1000)
            val result = repository.getVideoById(getIdByIndex(state.currentVideoIndex))
            when (result) {
                is Result.Error -> {
                    state = state.copy(
                        errorMessage = result.error.name,
                        isLoading = false,
                        currentVideo = null,
                    )
                }

                is Result.Success -> {
                    state = state.copy(
                        errorMessage = "",
                        isLoading = false,
                        currentVideo = result.data,
                    )
                    // exoplayer should be accessed on main thread
                    withContext(Dispatchers.Main) {
                        setMediaItem(state.currentVideo!!.getSdVideoUrl())
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("VideoPlayerVM", e.message.toString())
            state = state.copy(
                isLoading = false,
                errorMessage = "Oops... something went wrong.",
                currentVideo = null
            )
        }
    }

    private fun getIdByIndex(index: Int): Long {
        return state.videoList[index]
    }

    override fun onCleared() {
        super.onCleared()
        player.removeListener(listener)
        player.release()
    }
}
