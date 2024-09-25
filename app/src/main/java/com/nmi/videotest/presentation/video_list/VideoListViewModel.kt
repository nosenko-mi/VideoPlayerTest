package com.nmi.videotest.presentation.video_list


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmi.videotest.common.result.Result
import com.nmi.videotest.domain.video_repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoListViewModel(
    private val repository: VideoRepository
) : ViewModel() {
    var state by mutableStateOf(VideoListScreenState())
        private set

    init {
        loadVideos()
    }

    fun onAction(action: VideoListAction) {
        when (action) {
            is VideoListAction.Reload -> {
                loadVideos()
            }
            else -> {}
        }
    }

    fun loadVideos(page: Int = 1, perPage: Int = 15) {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(
                isLoading = true, videos = emptyList()
            )
            try {
                val result = repository.getVideos(page, perPage)
                when (result) {
                    is Result.Error -> {
                        state = state.copy(
                            errorMessage = result.error,
                            isLoading = false,
                            videos = emptyList()
                        )
                    }

                    is Result.Success -> {
                        state = state.copy(
                            errorMessage = null,
                            isLoading = false,
                            videos = result.data
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("VideoListVM", e.printStackTrace().toString())
                state = state.copy(isLoading = false, videos = emptyList())
            }
        }
    }
}