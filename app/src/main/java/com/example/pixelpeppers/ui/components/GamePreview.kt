package com.example.pixelpeppers.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun GamePreview(
    igdbArtworkId: String,
    description: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val igdbImagePrefix = "https://images.igdb.com/igdb/image/upload/t_cover_big/"
    GlideImage(
        model = igdbImagePrefix + igdbArtworkId,
        contentDescription = description,
        loading = placeholder(ColorPainter(MaterialTheme.colorScheme.onTertiary)),
        modifier = modifier.
            clickable(onClick = onClick)
            .padding(10.dp)
    ) {
        it.transform(CenterInside(), RoundedCorners(70))
    }
}