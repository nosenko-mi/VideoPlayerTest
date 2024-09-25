package com.nmi.videotest.presentation.video_player

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import com.nmi.videotest.presentation.ui.theme.spacing
import com.nmi.videotest.presentation.video_player.component.AnimatedIconButton
import com.nmi.videotest.presentation.video_player.component.ErrorContainer
import org.koin.androidx.compose.koinViewModel


@Composable
fun VideoPlayerScreenRoot(
    navController: NavHostController,
    viewModel: VideoPlayerViewModel = koinViewModel<VideoPlayerViewModel>()
) {
    VideoPlayerScreen(
        state = viewModel.state,
        videoPlayer = viewModel.player,
        onAction = { action ->
            when (action) {
                is VideoPlayerAction.NavigateBack -> {
                    navController.navigateUp()
                }

                else -> {}
            }
            viewModel.onAction(action)
        },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(
    state: VideoPlayerScreenState,
    videoPlayer: Player,
    onAction: (VideoPlayerAction) -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onAction(VideoPlayerAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!isInternetAvailable(context) || state.errorMessage.isNotBlank()){
                    ErrorContainer(state.errorMessage.ifBlank{"No internet connection"})
                }
                else if (state.isLoading && state.errorMessage.isBlank()) {
                    CircularProgressIndicator()
                }else {
                    VideoContent(videoPlayer = videoPlayer, onAction = onAction, state = state)
                }
            }
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.extraLarge))
            Text(
                text = "Video provided by Pexels",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(MaterialTheme.spacing.medium)
            )

        }
    }
}

@Composable
fun VideoContent(
    state: VideoPlayerScreenState,
    videoPlayer: Player,
    onAction: (VideoPlayerAction) -> Unit
) {
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    Column(
        modifier = Modifier
            .padding(MaterialTheme.spacing.medium)
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = videoPlayer
                }
            },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()
                        it.player?.pause()
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        it.onResume()
                    }

                    else -> Unit
                }
            },
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .fillMaxWidth()
                .heightIn(0.dp, 450.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = { onAction(VideoPlayerAction.PreviousVideo) },
                modifier = Modifier.width(116.dp)
            ) {
                Text(text = "Previous")
            }
            AnimatedIconButton(
                modifier = Modifier
                    .clip(
                        CircleShape
                    )
                    .background(MaterialTheme.colorScheme.primary),
                iconTint = MaterialTheme.colorScheme.onPrimary,
                isPressed = state.isPlaying
            ) {
                onAction(VideoPlayerAction.StartPause)
            }

            OutlinedButton(
                onClick = { onAction(VideoPlayerAction.NextVideo) },
                modifier = Modifier.width(116.dp)
            ) {
                Text(text = "Next")
            }
        }
    }
}

private fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }
    return result
}


