package com.nmi.videotest.domain.model

data class VideoFileModel(
    val id: Long,
    val link: String,
    val quality: VideoQuality,
    val fileType: String,
    val width: Int,
    val height: Int,
    val fps: Float
)
