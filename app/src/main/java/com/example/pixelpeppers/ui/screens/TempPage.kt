package com.example.pixelpeppers.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pixelpeppers.Route
import com.example.pixelpeppers.coordinators.dataCoordinator.DataCoordinator
import com.example.pixelpeppers.clients.UserClient
import com.example.pixelpeppers.ui.components.PixelPeppersButton
import com.example.pixelpeppers.viewModels.UserViewModel


@Composable
fun TempPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
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
            userViewModel.startTwitchAuthActivity(context)
        })
    }

//    GameCarousell(artworkIDs = artworks, modifier = modifier)
}