package com.example.pixelpeppers.ui.screens

import LoadingAnimation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pixelpeppers.R
import com.example.pixelpeppers.models.ImageSize
import com.example.pixelpeppers.ui.components.GamePreview
import com.example.pixelpeppers.ui.components.GenreTag
import com.example.pixelpeppers.ui.components.ReviewBlock
import com.example.pixelpeppers.ui.components.ReviewDialog
import com.example.pixelpeppers.viewModels.GameViewModel
import com.example.pixelpeppers.viewModels.ReviewViewModel

@Composable
fun GamePage(
    modifier: Modifier = Modifier,
    gameID: Int = 17000,
    gameViewModel: GameViewModel = hiltViewModel(),
    reviewViewModel: ReviewViewModel = hiltViewModel(),
) {
    val showReviewDialog = remember { mutableStateOf(false) }
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

                else -> 1f
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val game by gameViewModel.getGameById(gameID).observeAsState()
        val reviews by reviewViewModel.getReviewsByGameId(gameID).observeAsState()

        LaunchedEffect(Unit) {
            gameViewModel.refreshGamesByID(gameID)
            reviewViewModel.refreshReviewsByGameId(gameID)
        }
        if (game == null || reviews == null) {
            LoadingAnimation()
        } else {
            val gameRes = game!!

            if (showReviewDialog.value) {
                ReviewDialog(
                    closeDialog = {
                        showReviewDialog.value = false
                    },
                    gameID = gameID
                )
            }

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                LazyColumn(
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

                    item {
                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                        )
                    }
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = gameRes.name,
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold,
                                maxLines = 1,
                                modifier = Modifier
                                    .padding(start = 10.dp)
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                modifier = Modifier
                                    .padding(start = 10.dp, bottom = 10.dp)
                            ) {
                                repeat(3) { index ->
                                    if (index < gameRes.genres.size) {
                                        GenreTag(
                                            text = gameRes.genres[index].name,
                                            clickable = false,
                                            modifier = Modifier
                                                .height(30.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    val loading: Array<Boolean> = reviews!!.map { true }.toTypedArray()

                    itemsIndexed(reviews!!) { index, review ->
                        ReviewBlock(review = review, onFinishLoading = { loading[index] = false })
                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                        )
                        if (index == reviews!!.size - 1) {
                            Spacer(
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background)
                            )
                        }
                    }
                    if (loading.any { it }) {
                        item {
                            LoadingAnimation()
                        }
                    }
                }
                FloatingActionButton(
                    onClick = {
                        showReviewDialog.value = true
                    },
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(bottom = 40.dp, end = 10.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = "Add Comment",
                        tint = Color.White,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(10.dp)
                    )
                }
            }
        }
    }
}

