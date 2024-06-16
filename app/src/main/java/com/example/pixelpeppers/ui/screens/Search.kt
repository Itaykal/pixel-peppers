package com.example.pixelpeppers.ui.screens

import LoadingAnimation
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.ui.components.GamePreview
import com.example.pixelpeppers.ui.components.PixelPeppersSearchBar
import com.example.pixelpeppers.viewModels.GameViewModel
import kotlinx.coroutines.launch


@Composable
fun Search(
    onGameClick: (Game) -> Unit,
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = hiltViewModel()
) {
    val searchQuery = remember { mutableStateOf("") }
    val games by gameViewModel.getGamesBySearch(searchQuery.value).observeAsState()
    val searchState = rememberLazyGridState()

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PixelPeppersSearchBar(
                onSearch = {
                    searchQuery.value = it
                    gameViewModel.refreshGamesBySearch(it)
                }
            )
            if (games == null) {
                LoadingAnimation()
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    state = searchState
                ) {
                    items(games!!) {
                        GamePreview(
                            game = it,
                            modifier = modifier,
                            onClick = { onGameClick(it) }
                        )
                    }
                }
            }
        }
    }
}