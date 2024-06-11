package com.example.pixelpeppers.ui.screens

import LoadingAnimation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pixelpeppers.Route
import com.example.pixelpeppers.models.Cover
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.models.Genre
import com.example.pixelpeppers.repositories.GamesRepository
import com.example.pixelpeppers.ui.components.GameCarousell
import com.example.pixelpeppers.ui.components.GenreTag
import com.example.pixelpeppers.ui.components.PageIndicator
import com.example.pixelpeppers.ui.components.PixelPeppersButton


var OnboardingTagValues = listOf<String>(
    "Action", "Adventure", "Indie", "Rouge Like", "Shooter", "RPG", "Strategy", "Sports", "Puzzle",
    "MMO", "Rhythm", "Card", "Horror", "Gacha", "Sandbox"
)
var g = Game(
    id = 17000,
    name = "Stardew Valley",
    cover = Cover("//images.igdb.com/igdb/image/upload/t_cover_big/xrpmydnu9rpxvxfjkiu7.jpeg"),
    genres = listOf<Genre>(Genre("Indie"), Genre("Farming"))
)
//var gameCoversTop = listOf<String>("co79vq", "xrpmydnu9rpxvxfjkiu7", "co2l7z")
//var gameCoversBottom = listOf<String>("co1nh1", "co39vc", "co4b39")


@Composable
fun Onboarding(
    modifier: Modifier = Modifier,
    page: Int = 0,
    navController: NavController,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(0.dp)
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = modifier
        ) {
            PageIndicator(totalPages = 2, currentPage = page)
            PixelPeppersButton(
                text = "Next",
                onClick = {
                if (page == 0) {
                    navController.navigate(route = Route.OnboardingTags.route)
                } else {
                    navController.navigate(route = Route.Login.route)
                }
            })
            if (page == 0) {
                OnboardingIntro(
                    modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                )
            } else if (page == 1) {
                OnboardingTags(
                    modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                )
            }
        }
    }
}



@Composable
fun OnboardingTags(
    modifier: Modifier = Modifier,
) {
    val rows = 7
    val columns = 3

    val genres = remember { mutableStateListOf<Genre>() }
    val loading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        GamesRepository.getGenres (limit = rows * columns) {
            genres.addAll(it)
            loading.value = false
        }
    }


    if (loading.value) {
        Box (
            contentAlignment = Alignment.Center,
            modifier = modifier
        ) {
            // TODO: Add loader here
            LoadingAnimation()
        }
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
        ) {
            Text(
                text = "Select the genres you\nlike to play",
                color = Color.White,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .align(alignment = Alignment.TopCenter)
                    .offset(
                        x = 0.dp,
                        y = 597.dp
                    )
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(-8.dp),
                modifier = modifier
                    .offset(y = 280.dp)
            ) {
                repeat(times = rows) { rowIndex ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            8.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth() // Ensures the row takes up the full width
                    ) {
                        repeat(times = columns) { columnIndex ->
                            val index = rowIndex * 4 + columnIndex
                            if (index < genres.size) {
                                GenreTag(
                                    text = genres[index].name,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun OnboardingIntro(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier,
    ) {
        Text(
            text = "Tell us about your\nfavorite games",
            color = Color.White,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .offset(
                    x = 0.dp,
                    y = 597.dp
                )
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .height(500.dp)
                .align(Alignment.TopCenter)
                .offset(y = 150.dp)
        ) {
            GameCarousell(
                games = listOf<Game>(g, g, g),
                static = true,
            )
            GameCarousell(
                games = listOf<Game>(g, g, g),
                static = true,
                modifier = Modifier
                    .offset(x = (-15).dp, y = (-20).dp)
                    .wrapContentWidth(unbounded = true)
                    .width(600.dp)
            )
        }
    }
}