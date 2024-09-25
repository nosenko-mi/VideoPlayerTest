package com.nmi.videotest.presentation.video_player

import com.nmi.videotest.common.result.NetworkError
import com.nmi.videotest.domain.model.VideoModel

//data class VideoPlayerScreenState (
//    val isLoading: Boolean = false,
//    val errorMessage: NetworkError? = null,
//    val videoList: List<Long> = emptyList(),
//    val currentVideo: VideoModel? = null,
//    val currentVideoUrl: String? = null,
//    val currentVideoIndex: Int = 0
//)

data class VideoPlayerScreenState (
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val videoList: List<Long> = emptyList(),
    val currentVideoIndex: Int = 0,
    val currentVideo: VideoModel? = null,
    val totalDuration: Long = 0L,
    val currentTime: Long = 0L,
    val bufferedPercentage: Int = 0
)
