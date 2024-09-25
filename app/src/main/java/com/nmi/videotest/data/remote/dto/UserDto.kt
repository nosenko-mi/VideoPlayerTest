package com.nmi.videotest.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.nmi.videotest.data.local.entity.UserEntity
import com.nmi.videotest.domain.model.UserModel

data class UserDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

fun UserDto.toModel(): UserModel {
    return UserModel(id=this.id, name=this.name)
}

fun UserDto.toEntity(): UserEntity {
    return UserEntity(id=this.id, name=this.name, url = this.url)
}