package com.nmi.videotest.presentation.video_player.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun AnimatedIconButton(
    modifier: Modifier = Modifier,
    iconStart: ImageVector = Icons.Default.PlayArrow,
    iconEnd: ImageVector = Icons.Default.Stop,
    iconTint: Color = Color.Cyan,
    isPressed: Boolean = false,
    onClick: () -> Unit,
) {
    Column {
        IconButton(
            modifier = modifier,
            onClick = onClick
        ) {
            AnimatedContent(
                targetState = isPressed,
                transitionSpec = {
                    if (targetState) {
                        (slideInVertically { height -> height } + fadeIn()).togetherWith(
                            slideOutVertically { height -> -height } + fadeOut())
                    } else {
                        (slideInVertically { height -> -height } + fadeIn()).togetherWith(
                            slideOutVertically { height -> height } + fadeOut())
                    }.using(
                        SizeTransform(clip = false)
                    )
                },
                label = ""
            ) {
                Icon(
                    imageVector = if (it) iconEnd else iconStart,
                    contentDescription = "Play/Stop",
                    tint = iconTint
                )
            }

        }
    }
}