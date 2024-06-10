package com.example.pixelpeppers.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GameCarousell(
    artworkIDs: List<String>,
    titleOn: Boolean = true,
    userScrollEnabled: Boolean = true,
    modifier: Modifier,
) {
    val state = rememberLazyListState()
    Box (
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        LazyRow (
            state = state,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            userScrollEnabled = userScrollEnabled,
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 10.dp),
            modifier = Modifier
                .fillMaxHeight()
        ) {
            itemsIndexed(artworkIDs) {_, artwork ->
                GamePreview(
                    igdbArtworkId = artwork,
                    description = "",
                    titleOn = titleOn,
                )
            }
        }
    }
}