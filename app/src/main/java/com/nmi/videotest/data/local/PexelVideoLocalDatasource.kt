package com.nmi.videotest.data.local

import com.nmi.videotest.data.local.entity.toModel
import com.nmi.videotest.data.remote.dto.VideoDto
import com.nmi.videotest.data.remote.dto.toEntity
import com.nmi.videotest.domain.model.VideoModel

class PexelVideoLocalDatasource(
    private val db: AppDatabase
) {

    suspend fun getVideoById(id: Long): VideoModel {
        val result = db.videoDao().getById(id)
        return result.toModel()
    }

    suspend fun getVideos(page: Int, perPage: Int): List<VideoModel> {
        // offset is 0-based. Could use pagination extension.
        val result = db.videoDao().getAll(limit = perPage, offset = (page-1)*perPage)
        return result.map { it.toModel() }

    }

    suspend fun insertVideos(videos: List<VideoDto>){
        db.videoDao().insertAll(videos.map { it.toEntity() })
    }

}