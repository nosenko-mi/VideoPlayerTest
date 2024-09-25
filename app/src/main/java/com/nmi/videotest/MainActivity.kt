package com.nmi.videotest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nmi.videotest.presentation.video_player.VideoPlayerScreen
import com.nmi.videotest.presentation.video_list.VideoListScreen
import com.nmi.videotest.presentation.ui.theme.VideoPlayerTestTheme
import com.nmi.videotest.presentation.video_list.VideoListScreenRoot
import com.nmi.videotest.presentation.video_player.VideoPlayerScreenRoot
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideoPlayerTestTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = VideoListNavScreen
                ) {
                    composable<VideoListNavScreen> {
                        VideoListScreenRoot(navController)
                    }

                    composable<VideoPlayerNavScreen> {
                        VideoPlayerScreenRoot(navController)
                    }
                }
            }
        }
    }
}

@Serializable
object VideoListNavScreen

@Serializable
data class VideoPlayerNavScreen(
    val position: Int,
    val videoIds: List<Long>
)
