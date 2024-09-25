package com.nmi.videotest.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nmi.videotest.domain.model.UserModel

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String
)

fun UserEntity.toModel(): UserModel{
    return UserModel(id = this.id, name = this.name)
}