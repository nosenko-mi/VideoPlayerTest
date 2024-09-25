package com.nmi.videotest.data.repository

import com.nmi.videotest.common.result.NetworkError
import com.nmi.videotest.common.result.Result
import com.nmi.videotest.data.local.PexelVideoLocalDatasource
import com.nmi.videotest.data.remote.PexelVideoRemoteDatasource
import com.nmi.videotest.data.remote.dto.toModel
import com.nmi.videotest.domain.model.VideoModel
import com.nmi.videotest.domain.video_repository.VideoRepository

class PexelVideoRepository(
    private val remoteDatasource: PexelVideoRemoteDatasource,
    private val localDatasource: PexelVideoLocalDatasource
): VideoRepository {
    override suspend fun getVideos(page: Int, perPage: Int): Result<List<VideoModel>, NetworkError> {
        return when (val result = remoteDatasource.getVideos(page, perPage)){
            is Result.Error -> { // Ideally, should check errors. Since I do not get network state, I left it as is.
                val local = localDatasource.getVideos(page, perPage)
                if (local.isNotEmpty()){
                    Result.Success(local)
                } else{
                    Result.Error(result.error)
                }

            }
            is Result.Success -> {
                localDatasource.insertVideos(result.data)
                Result.Success(result.data.toModel())
            }
        }
    }

    override suspend fun getVideoById(id: Long): Result<VideoModel, NetworkError> {
        return when (val result = remoteDatasource.getVideoById(id)){
            is Result.Error -> {
                Result.Error(result.error)
            }
            is Result.Success -> {
                Result.Success(result.data.toModel())
            }
        }
    }

}