package com.example.pixelpeppers.ui.screens

import LoadingAnimation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.models.ImageSize
import com.example.pixelpeppers.repositories.GameRepository
import com.example.pixelpeppers.ui.components.GamePreview

@Composable
fun GamePage(
    modifier: Modifier = Modifier,
    gameID: Int = 17000,
) {
    val lazyListState = rememberLazyListState()
    val firstItemTranslationY by remember {
        derivedStateOf {
            when {
                lazyListState.layoutInfo.visibleItemsInfo.isNotEmpty()
                        && lazyListState.firstVisibleItemIndex == 0 -> lazyListState.firstVisibleItemScrollOffset * 0.9f
                else -> 0f
            }
        }
    }
    val visibility by remember {
        derivedStateOf {
            when {
                lazyListState.layoutInfo.visibleItemsInfo.isNotEmpty() && lazyListState.firstVisibleItemIndex == 0 -> {
                    val imageSize = lazyListState.layoutInfo.visibleItemsInfo[0].size
                    val scrollOffset = lazyListState.firstVisibleItemScrollOffset

                    scrollOffset / imageSize.toFloat()
                }
                else                                                                                               -> 1f
            }
        }
    }


    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val game = remember { mutableStateOf<Game?>(null) }
        LaunchedEffect(Unit) {
            GameRepository.instance.getGame(gameID).let {
                game.value = it
            }
        }
        if (game.value == null) {
            LoadingAnimation()
        } else {
            val gameRes = game.value!!
            Box (
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                LazyColumn (
                    state = lazyListState,
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp)
                ) {
                    item {
                        GamePreview(
                            game = gameRes,
                            titleOn = false,
                            clipping = false,
                            imageSize = ImageSize.COVER_BIG,
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.TopCenter,
                            modifier = modifier
                                .height(350.dp)
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                                .graphicsLayer {
                                    translationY = firstItemTranslationY
                                    alpha = 1f - visibility
                                }
                        )
                    }
                    items(66) {index ->
                        // @@ TODO: Implement comment fetching and component.
                        Text(
                            text = index.toString(),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                        )
                    }
                }
            }
        }
    }
}
