package com.nmi.videotest.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nmi.videotest.data.local.entity.VideoPictureEntity

@Dao
interface VideoPictureDao {
    @Query("SELECT * FROM video_picture")
    fun getAll(): List<VideoPictureEntity>

    @Query("SELECT * FROM video_picture WHERE id IN (:pictureIds)")
    fun loadAllByIds(pictureIds: LongArray): List<VideoPictureEntity>

    @Insert
    fun insertAll(vararg files: VideoPictureEntity)

    @Delete
    fun delete(file: VideoPictureEntity)
}