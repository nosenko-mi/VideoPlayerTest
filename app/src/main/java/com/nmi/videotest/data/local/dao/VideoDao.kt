package com.nmi.videotest.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nmi.videotest.data.local.entity.VideoEntity

@Dao
interface VideoDao {
    @Query("SELECT * FROM video LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int, ): List<VideoEntity>

    @Query("SELECT * FROM video WHERE id =(:id)")
    fun getById(id: Long): VideoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videos: List<VideoEntity>)

    @Delete
    fun delete(video: VideoEntity)
}