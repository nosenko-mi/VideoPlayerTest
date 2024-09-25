package com.nmi.videotest.di

import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nmi.videotest.common.Constants
import com.nmi.videotest.data.local.AppDatabase
import com.nmi.videotest.data.local.PexelVideoLocalDatasource
import com.nmi.videotest.data.remote.PexelVideoApi
import com.nmi.videotest.data.remote.PexelVideoRemoteDatasource
import com.nmi.videotest.data.repository.PexelVideoRepository
import com.nmi.videotest.domain.video_repository.VideoRepository
import com.nmi.videotest.presentation.video_list.VideoListViewModel
import com.nmi.videotest.presentation.video_player.VideoPlayerViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single<PexelVideoApi> {
        Retrofit.Builder()
            .baseUrl("https://api.pexels.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PexelVideoApi::class.java)
    }

    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication().applicationContext,
            AppDatabase::class.java,
            "app-db"
        ).build()
    }

    single<PexelVideoRemoteDatasource> {
        PexelVideoRemoteDatasource(api = get())
    }

    single<PexelVideoLocalDatasource> {
        PexelVideoLocalDatasource(db = get())
    }

    single<VideoRepository> {
        PexelVideoRepository(remoteDatasource = get(), localDatasource = get())
    }

    factory<Player> {
        ExoPlayer.Builder(androidApplication().applicationContext)
            .build()
    }

    viewModel { VideoListViewModel(repository = get()) }
    viewModel { VideoPlayerViewModel(savedStateHandle = get(), repository = get(), player = get()) }
}