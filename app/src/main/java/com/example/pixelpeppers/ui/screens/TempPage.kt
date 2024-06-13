package com.example.pixelpeppers.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

class GenresViewModel

@Composable
fun TempPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    ) {
    if (DataCoordinator.instance.accessToken != null) {
        navController.navigate(Route.OnboardingIntro.route)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
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

//    GameCarousell(artworkIDs = artworks, modifier = modifier)
}