package com.nmi.videotest.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.nmi.videotest.domain.model.VideoModel

data class PexelVideoBatchResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("videos")
    val videos: List<VideoDto>
)

fun PexelVideoBatchResponse.toModel(): List<VideoModel> {
    return this.videos.map { it.toModel() }
}