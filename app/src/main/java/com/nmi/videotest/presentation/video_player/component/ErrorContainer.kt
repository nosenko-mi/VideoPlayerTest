package com.nmi.videotest.presentation.video_player.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ErrorContainer(
    errorMessage: String = ""
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = errorMessage.ifBlank { "Oops... I could not load this video" },
            color = MaterialTheme.colorScheme.error
        )
    }
}
