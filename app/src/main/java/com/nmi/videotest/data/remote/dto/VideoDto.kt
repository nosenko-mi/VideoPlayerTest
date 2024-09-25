package com.nmi.videotest.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.nmi.videotest.data.local.entity.VideoEntity
import com.nmi.videotest.domain.model.VideoModel

data class VideoDto(
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: Long,
    @SerializedName("image")
    val image: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("user")
    val user: UserDto,
    @SerializedName("video_files")
    val videoFiles: List<VideoFileDto>,
    @SerializedName("video_pictures")
    val videoPictures: List<VideoPictureDto>,
    @SerializedName("width")
    val width: Int
)

fun VideoDto.toModel(): VideoModel {
    return VideoModel(
        id = this.id,
        imageUrl = this.image,
        url = this.url,
        videoFiles = this.videoFiles.map { it.toModel() },
        pictures = this.videoPictures.map { it.toModel() },
        user = this.user.toModel()
    )
}

fun List<VideoDto>.toModel(): List<VideoModel> {
    return this.map { it.toModel() }
}

fun VideoDto.toEntity(): VideoEntity {
    return VideoEntity(id=this.id, imageUrl = this.image, url = this.url, user = this.user.toEntity())
}

fun List<VideoDto>.toEntity(): List<VideoEntity> {
    return this.map { it.toEntity() }
}