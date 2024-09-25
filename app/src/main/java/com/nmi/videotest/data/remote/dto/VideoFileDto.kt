package com.nmi.videotest.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.nmi.videotest.domain.model.VideoFileModel
import com.nmi.videotest.domain.model.toVideoQuality

data class VideoFileDto(
    @SerializedName("file_type")
    val fileType: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: Long,
    @SerializedName("link")
    val link: String,
    @SerializedName("quality")
    val quality: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("fps")
    val fps: Float
)

fun VideoFileDto.toModel(): VideoFileModel {
    return VideoFileModel(
        id = this.id,
        link = this.link,
        quality = this.quality.toVideoQuality(),
        fileType = this.fileType,
        width = this.width,
        height = this.height,
        fps = this.fps
    )
}