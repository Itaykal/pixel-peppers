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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.pixelpeppers.Route
import com.example.pixelpeppers.coordinators.dataCoordinator.DataCoordinator
import com.example.pixelpeppers.models.Genre
import com.example.pixelpeppers.ui.components.GameCarousell
import com.example.pixelpeppers.repositories.GamesRepository
import com.example.pixelpeppers.repositories.UserRepository
import com.example.pixelpeppers.ui.components.PixelPeppersButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.repositories.GamesRepository
import com.example.pixelpeppers.ui.components.LargeGamePreview

@Composable
fun TempPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    ) {
    if (DataCoordinator.instance.accessToken != null) {
        navController.navigate(Route.OnboardingIntro.route)
) {
    val game = remember { mutableStateOf<Game?>(null) }
    val loading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        GamesRepository.getGame(17000) {
            game.value = it
            loading.value = false
        }
        val context = LocalContext.current
        val artworks: List<String> = listOf<String>(
            "co2k2z", "co6xe3", "co5ff7", "co1nic",
            "co670h", "co4jni", "co4a7a", "co1n1x",
            "co1sfj", "co5vmg", "co5xex"
        )
        PixelPeppersButton(text = "Login with twitch", onClick = {
            // run async call to authenticate with twitch on background thread
            CoroutineScope(Dispatchers.Main).launch {
                UserRepository.instance.authenticateWithTwitch(context)
            }
        })
    }

}