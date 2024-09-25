package com.nmi.videotest.domain.model

import com.nmi.videotest.data.local.entity.VideoEntity

data class VideoModel(
    val id: Long,
    val imageUrl: String,
    val url: String,
    val videoFiles: List<VideoFileModel>,
    val pictures: List<VideoPictureModel>,
    val user: UserModel
) {
    fun getSdVideoUrl(): String {
        // take first from sd videos. Good for now
        val sdVideo = videoFiles.first { it.quality == VideoQuality.SD }
        return sdVideo.link
    }
}