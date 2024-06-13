package com.example.pixelpeppers.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.pixelpeppers.R
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.models.ImageSize

val defaultImageURL = "https://images.igdb.com/igdb/image/upload/${ImageSize.DEFAULT.size}/xrpmydnu9rpxvxfjkiu7.jpeg"

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GamePreview(
    game: Game,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    titleOn: Boolean = true,
    imageSize: ImageSize = ImageSize.DEFAULT,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(PaddingValues(bottom = 25.dp))
    ) {
        var imageURL = game.cover?.url ?: defaultImageURL
        imageURL = imageURL.replace(ImageSize.THUMB.size, imageSize.size)
        Box(
            modifier = Modifier
                .clip(shape= MaterialTheme.shapes.extraLarge)
        )
        {
            GlideImage(
                model = "https:${imageURL}",
                contentDescription = game.name,
                loading = placeholder(R.drawable.avd_anim_placeholder),
                transition = CrossFade,
                alignment = alignment,
                contentScale = contentScale,
                modifier = modifier
                    .clickable(onClick = onClick)
                    .height(196.dp)
                    .width(150.dp),
            ) {
                it
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(RoundedCorners(70))
            }
        }
        if (titleOn) {
            var gameTitle = game.name?: "Placeholder"
            if (gameTitle.length > 15) {
                gameTitle = gameTitle.substring(0, 15) + "..."
            }
            Text(
                text = gameTitle,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(x = 0.dp, y = 25.dp)
            )
        }
    }
}