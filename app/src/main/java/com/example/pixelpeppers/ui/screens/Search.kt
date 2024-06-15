package com.example.pixelpeppers.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.repositories.GamesRepository
import com.example.pixelpeppers.ui.components.GamePreview
import com.example.pixelpeppers.ui.components.PixelPeppersSearchBar
import kotlinx.coroutines.launch


@Composable
fun Search(
    modifier: Modifier = Modifier,
) {
    val games = remember { mutableStateListOf<Game?>(null) }
    val searchState = rememberLazyGridState()
    val corutineScope = rememberCoroutineScope()
    val resetLazyGrid: () -> Unit = {
        corutineScope.launch {
            searchState.scrollToItem(0)
        }
    }

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp),
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PixelPeppersSearchBar(
                onSearch = {searchQuery ->
                    GamesRepository.searchGames(searchQuery, limit = 30) {
                        games.clear()
                        games.addAll(it)
                        resetLazyGrid()
                    }
                }
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                state = searchState
            ) {
                items(games) {
                    if (it != null) {
                        GamePreview(
                            game = it,
                            modifier = modifier
                        )
                    }
                }
            }
        }
    }
}