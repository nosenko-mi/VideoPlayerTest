package com.nmi.videotest.domain.model

sealed class VideoQuality {
    data object SD: VideoQuality()
    data object HD: VideoQuality()
    data object UNDEFINED: VideoQuality()
}

fun String.toVideoQuality(): VideoQuality{
    return when(this){
        "sd" -> VideoQuality.SD
        "hd" -> VideoQuality.HD
        else -> VideoQuality.UNDEFINED
    }
}