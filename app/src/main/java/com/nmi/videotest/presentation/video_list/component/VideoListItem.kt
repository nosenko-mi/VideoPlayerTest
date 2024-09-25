package com.nmi.videotest.presentation.video_list.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun VideoListItem(
    modifier: Modifier = Modifier,
    id: Long,
    imageUrl: String = "",
    onClick: (id: Long) -> Unit = {}
) {
    Card(
        onClick = { onClick(id) },
        modifier = modifier
            .widthIn(0.dp, 350.dp)
            .heightIn(0.dp, 170.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

    }
}