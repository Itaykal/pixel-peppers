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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pixelpeppers.R
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.ui.components.CircleIconButton
import com.example.pixelpeppers.ui.components.GameCarousell
import com.example.pixelpeppers.ui.components.LargeGamePreview
import com.example.pixelpeppers.viewModels.GameViewModel
import com.example.pixelpeppers.viewModels.UserViewModel

@Composable
fun MainMenu(
    onGameClick: (Game) -> Unit,
    onAccountClick: () -> Unit,
    onSearchClick: () -> Unit,
    returnToOnboarding: () -> Unit,
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
) {
    val user by userViewModel.getUser().observeAsState()
    val state = rememberLazyListState()
    val trendingGame by gameViewModel.getGameById(17000).observeAsState()
    val topics = listOf("Stardew Valley", "World of Warcraft", "Grand Theft Auto", "Call of Duty", "Spider-Man")
    val gamesMap = topics.associateWith { gameViewModel.getGamesBySearch(it) }

    LaunchedEffect(Unit) {
        gameViewModel.refreshGamesByID(17000)
        for (topic in topics) {
            gameViewModel.refreshGamesBySearch(topic)
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        if (trendingGame == null || gamesMap.size != topics.size || user == null) {
            LoadingAnimation()
        } else if (!user!!.onboardingComplete) {
            returnToOnboarding()
        } else {
            LazyColumn(
                state = state,
                verticalArrangement = Arrangement.spacedBy(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(top = 35.dp, bottom = 35.dp)

            ) {
                item {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),//, Alignment.End),
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp)
                    ) {
                        Text(
                            text = "Hey, ${user!!.displayName}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(Modifier.weight(1f))
                        CircleIconButton(
                            icon = painterResource(id = R.drawable.icons8_search),
                            description = "Search",
                            onClick = onSearchClick
                        )
                        CircleIconButton(
                            icon = painterResource(id = R.drawable.user),
                            description = "Account",
                            onClick = onAccountClick
                        )
                    }
                    // highlighted game
                    Row(
                        verticalAlignment = Alignment.Top,
                    ) {
                        LargeGamePreview(
                            game = trendingGame!!,
                            onClick = { onGameClick(trendingGame!!) },
                        )
                    }
                }

                items(gamesMap.keys.toList()) { key ->
                    val games by gamesMap[key]!!.observeAsState()
                    if (games.isNullOrEmpty()) {
                        LoadingAnimation()
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = key,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    maxLines = 1,
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                )

                                GameCarousell(
                                    games = games!!,
                                    onGameClick = onGameClick,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}