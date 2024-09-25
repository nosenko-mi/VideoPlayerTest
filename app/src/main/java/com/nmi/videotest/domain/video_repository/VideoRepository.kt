package com.nmi.videotest.domain.video_repository

import com.nmi.videotest.common.result.NetworkError
import com.nmi.videotest.common.result.Result
import com.nmi.videotest.domain.model.VideoModel

interface VideoRepository {
    suspend fun getVideos(
        page: Int = 1, perPage: Int = 15
    ): Result<List<VideoModel>, NetworkError>

    suspend fun getVideoById(id: Long): Result<VideoModel, NetworkError>
}