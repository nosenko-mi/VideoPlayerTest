package com.nmi.videotest.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.nmi.videotest.domain.model.VideoPictureModel

data class VideoPictureDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("nr")
    val nr: Int,
    @SerializedName("picture")
    val picture: String
)

fun VideoPictureDto.toModel(): VideoPictureModel{
    return VideoPictureModel(
        id = this.id,
        url = this.picture
    )
}