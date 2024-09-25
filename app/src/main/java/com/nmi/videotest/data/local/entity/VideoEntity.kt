package com.nmi.videotest.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nmi.videotest.domain.model.VideoModel


@Entity(tableName = "video")
data class VideoEntity(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val url: String,
    @Embedded(prefix = "user")
    val user: UserEntity,
)


fun VideoEntity.toModel(): VideoModel {
    return VideoModel(
        id = this.id,
        imageUrl = this.imageUrl,
        videoFiles = emptyList(),
        pictures = emptyList(),
        user = this.user.toModel(),
        url = this.url
    )
}
