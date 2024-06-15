package com.example.pixelpeppers.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.models.ImageSize

@Composable
fun LargeGamePreview(
    game: Game,
    modifier: Modifier = Modifier,
    imageHeight: Dp = 250.dp,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .border(
                width = 0.dp,
                color = Color.Transparent,
                shape = MaterialTheme.shapes.extraLarge
            )
            .padding(PaddingValues(8.dp))
            .height(imageHeight + 50.dp)
    ) {
        Box (
        ) {
            GamePreview(
                game = game,
                modifier = modifier
                    .clickable(onClick = onClick)
                    .height(imageHeight)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                imageSize = ImageSize.COVER_BIG
            )

        }
        Box(
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.onTertiary)
                .height(100.dp)
                .width(370.dp)
                .align(Alignment.BottomCenter)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy((-5).dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(15.dp)
            ) {
                Text(
                    "TRENDING",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    softWrap = true
                )
                Text(
                    game.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    softWrap = true
                )
                Row (
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 10.dp),
                ) {
                    repeat(times = 3) {index ->
                        if (index < game.genres.size) {
                            GenreTag(
                                text = game.genres[index].name,
                                clickable = false,
                                modifier = Modifier
                                    .height(30.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}