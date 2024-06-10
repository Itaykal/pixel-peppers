package com.example.pixelpeppers.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.pixelpeppers.ui.components.GameCarousell
import com.example.pixelpeppers.ui.components.GenreTag
import com.example.pixelpeppers.ui.components.PageIndicator
import com.example.pixelpeppers.ui.components.PixelPeppersButton


var OnboardingTagValues = listOf<String>(
    "Action", "Adventure", "Indie", "Rouge Like", "Shooter", "RPG", "Strategy", "Sports", "Puzzle",
    "MMO", "Rhythm", "Card", "Horror", "Gacha", "Sandbox"
)
var gameCoversTop = listOf<String>("co79vq", "xrpmydnu9rpxvxfjkiu7", "co2l7z")
var gameCoversBottom = listOf<String>("co1nh1", "co39vc", "co4b39")

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
        Box(contentAlignment = Alignment.TopCenter,
            modifier = modifier
        ) {
            PageIndicator(totalPages = 2, currentPage = page)
            PixelPeppersButton(text = "Next", onClick = {
                if (page == 0) {
                    navController.navigate(route = Route.OnboardingTags.route)
                } else {
                    navController.navigate(route = Route.Login.route)
                }
            })
            if (page == 0) {
                OnboardingIntro(
                    navController = navController,
                    modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                )
            } else if (page == 1) {
                OnboardingTags(
                    navController = navController,
                    modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                )
            }
        }
    }

}
@Composable
fun OnboardingTags(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    var rows = 4
    var columns = 4

    Box(contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(
            text = "Select the genres you\nlike to play",
            color = Color.White,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium),
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .offset(
                    x = 0.dp,
                    y = 597.dp
                ))
        Column(
            verticalArrangement = Arrangement.spacedBy(-8.dp),
            modifier = modifier
                .offset(y = 280.dp)
        ) {
            repeat(times = rows) { rowIndex ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth() // Ensures the row takes up the full width
                ) {
                    repeat(times = columns) { columnIndex ->
                        val index = rowIndex * 4 + columnIndex
                        if (index < OnboardingTagValues.size) {
                            GenreTag(
                                text = OnboardingTagValues[index],
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingIntro(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Box(contentAlignment = Alignment.TopCenter,
        modifier = modifier,
    ) {
        Text(
            text = "Tell us about your\nfavorite games",
            color = Color.White,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium),
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .offset(
                    x = 0.dp,
                    y = 597.dp
                ))
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .height(500.dp)
                .align(Alignment.TopCenter)
                .offset(y = 150.dp)
        ) {
            GameCarousell(
                artworkIDs = gameCoversTop,
                titleOn = false,
                userScrollEnabled = false,
            )
            GameCarousell(
                artworkIDs = gameCoversBottom,
                titleOn = false,
                userScrollEnabled = false,
                modifier = Modifier
                    .offset(y = -20.dp)
            )
        }
    }
}