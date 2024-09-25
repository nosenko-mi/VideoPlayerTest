package com.nmi.videotest.presentation.video_list

import com.nmi.videotest.common.result.NetworkError
import com.nmi.videotest.domain.model.VideoModel

data class VideoListScreenState (
    val isLoading: Boolean = false,
    val permissionDialogVisible: Boolean = false,
    val errorMessage: NetworkError? = null,
    val videos: List<VideoModel> = emptyList(),
)
