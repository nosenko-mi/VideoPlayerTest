package com.nmi.videotest.data.remote


import com.nmi.videotest.common.Constants.API_KEY
import com.nmi.videotest.data.remote.dto.PexelVideoBatchResponse
import com.nmi.videotest.data.remote.dto.VideoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelVideoApi {
    @Headers("Authorization: $API_KEY")
    @GET("/videos/popular")
    suspend fun getVideos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 15
    ): Response<PexelVideoBatchResponse>

    @Headers("Authorization: $API_KEY")
    @GET("/videos/videos/{id}")
    suspend fun getVideoById(@Path("id") id: Long): Response<VideoDto>
}