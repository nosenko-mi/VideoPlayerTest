package com.nmi.videotest.presentation.video_list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.nmi.videotest.VideoPlayerNavScreen
import com.nmi.videotest.presentation.ui.theme.spacing
import com.nmi.videotest.presentation.video_list.component.VideoListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun VideoListScreenRoot(
    navController: NavHostController,
    viewModel: VideoListViewModel = koinViewModel<VideoListViewModel>()
) {
    VideoListScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is VideoListAction.GoToVideoPlayer -> {
                    navController.navigate(
                        VideoPlayerNavScreen(
                            position = action.currentVideoPosition,
                            videoIds = action.videosIds
                        )
                    )
                }
                else -> {}
            }
            viewModel.onAction(action)
        }
    )
}


@Composable
fun VideoListScreen(
    state: VideoListScreenState,
    onAction: (VideoListAction) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    Scaffold { padding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (state.isLoading){
                    CircularProgressIndicator()
                } else if (state.errorMessage != null) {
                    Text(
                        text = state.errorMessage.toString(),
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))
                    IconButton(onClick = { onAction(VideoListAction.Reload) }) {
                        Icon(imageVector = Icons.Default.Replay, contentDescription = "Reload")
                    }
                } else{
                    LazyColumn(
                        state = lazyListState,
                        contentPadding = PaddingValues(
                            horizontal = 0.dp,
                            vertical = MaterialTheme.spacing.medium
                        ),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
                    ) {
                        items(state.videos) { video ->
                            VideoListItem(
                                imageUrl = video.imageUrl,
                                id = video.id,
                                onClick = {

                                    Log.d("VideoListScreen", state.videos.indexOf(video).toString())
                                    Log.d(
                                        "VideoListScreen",
                                        state.videos.map { it.id }.toList().toString()
                                    )
                                    onAction(
                                        VideoListAction.GoToVideoPlayer(
                                            currentVideoPosition = state.videos.indexOf(video),
                                            videosIds = state.videos.map { it.id }
                                        )
                                    )
                                }
                            )
                        }
                    }
                }

            }
        }

    }
}


