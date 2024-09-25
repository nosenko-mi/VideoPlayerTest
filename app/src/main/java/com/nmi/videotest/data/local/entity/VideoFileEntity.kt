package com.nmi.videotest.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_file")
data class VideoFileEntity(
    @PrimaryKey
    val id: Long,
    val link: String,
    val quality: String,
    @ColumnInfo(name = "file_type")
    val fileType: String,
    val width: Int,
    val height: Int,
    val fps: Float
)
