package com.nmi.videotest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nmi.videotest.data.local.dao.UserDao
import com.nmi.videotest.data.local.dao.VideoDao
import com.nmi.videotest.data.local.dao.VideoFileDao
import com.nmi.videotest.data.local.dao.VideoPictureDao
import com.nmi.videotest.data.local.entity.UserEntity
import com.nmi.videotest.data.local.entity.VideoEntity
import com.nmi.videotest.data.local.entity.VideoFileEntity
import com.nmi.videotest.data.local.entity.VideoPictureEntity

@Database(
    entities = [UserEntity::class, VideoEntity::class, VideoFileEntity::class, VideoPictureEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun videoDao(): VideoDao
    abstract fun videoFileDao(): VideoFileDao
    abstract fun videoPictureDao(): VideoPictureDao
}