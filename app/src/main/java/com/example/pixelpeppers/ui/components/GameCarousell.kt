package com.example.pixelpeppers.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.models.ImageSize

@Composable
fun GameCarousell(
    games: List<Game>,
    modifier: Modifier = Modifier,
    static: Boolean = false,
) {
    val state = rememberLazyListState()
    Box (
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .height(230.dp)
    ) {
        LazyRow (
            state = state,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            userScrollEnabled = !static,
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 10.dp),
            modifier = modifier
                .height(230.dp)
        ) {
            itemsIndexed(games) {_, game ->
                GamePreview(
                    game = game,
                    titleOn = !static,
                    imageSize = ImageSize.COVER_BIG,
                )
            }
        }
    }
}