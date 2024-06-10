package com.example.pixelpeppers.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pixelpeppers.ui.components.GamePreview

@Composable
fun TempPage(
    modifier: Modifier = Modifier,
) {
    GamePreview(
        igdbArtworkId = "co2k2z.jpeg",
        description = "brother",
        modifier = modifier
    )
}