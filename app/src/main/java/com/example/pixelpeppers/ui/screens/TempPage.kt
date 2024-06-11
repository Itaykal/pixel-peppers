package com.example.pixelpeppers.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.models.Genre
import com.example.pixelpeppers.ui.components.LargeGamePreview

@Composable
fun TempPage(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.background).fillMaxSize()
    ) {
        var g = Game(
            id = 17000,
            name = "Stardew Valley",
            posterURL = "https://images.igdb.com/igdb/image/upload/t_cover_big/xrpmydnu9rpxvxfjkiu7.jpeg",
            genres = listOf<Genre>(Genre("Indie"), Genre("Farming"))
        )
        LargeGamePreview(game = g)
    }
}