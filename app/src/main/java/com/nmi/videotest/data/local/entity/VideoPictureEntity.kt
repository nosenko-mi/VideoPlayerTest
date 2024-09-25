package com.nmi.videotest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_picture")
data class VideoPictureEntity(
    @PrimaryKey
    val id: Long,
    val url: String
)
