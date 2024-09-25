package com.nmi.videotest.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nmi.videotest.data.local.entity.VideoFileEntity

@Dao
interface VideoFileDao {
    @Query("SELECT * FROM video_file")
    fun getAll(): List<VideoFileEntity>

    @Query("SELECT * FROM video_file WHERE id IN (:fileIds)")
    fun loadAllByIds(fileIds: LongArray): List<VideoFileEntity>

    @Insert
    fun insertAll(vararg files: VideoFileEntity)

    @Delete
    fun delete(file: VideoFileEntity)
}