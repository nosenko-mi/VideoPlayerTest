package com.nmi.videotest.presentation.video_list

sealed interface VideoListAction {
    data object Reload: VideoListAction
    data class GoToVideoPlayer(val currentVideoPosition: Int, val videosIds: List<Long>): VideoListAction
}