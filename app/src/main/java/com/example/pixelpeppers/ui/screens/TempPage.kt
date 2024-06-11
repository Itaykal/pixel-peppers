package com.example.pixelpeppers.ui.screens

import LoadingAnimation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.repositories.GamesRepository
import com.example.pixelpeppers.ui.components.LargeGamePreview

@Composable
fun TempPage(
    modifier: Modifier = Modifier,
) {
    val game = remember { mutableStateOf<Game?>(null) }
    val loading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        GamesRepository.getGame(17000) {
            game.value = it
            loading.value = false
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        if (loading.value) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
            ) {
                LoadingAnimation(
                    modifier = modifier
                )
            }
        } else {
            if (game.value != null) {
                LargeGamePreview(game = game.value!!)
            }
        }

    }

}