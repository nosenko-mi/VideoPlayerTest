package com.nmi.videotest.presentation.video_player

sealed interface VideoPlayerAction {
    data object StartPause: VideoPlayerAction
    data object Forward: VideoPlayerAction
    data object Rewind: VideoPlayerAction
    data class SeekChange(val timeMs: Float): VideoPlayerAction
    data object NextVideo: VideoPlayerAction
    data object PreviousVideo: VideoPlayerAction
    data object NavigateBack: VideoPlayerAction
}