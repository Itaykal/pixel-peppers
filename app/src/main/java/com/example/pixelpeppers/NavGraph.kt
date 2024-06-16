package com.example.pixelpeppers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pixelpeppers.models.Game
import com.example.pixelpeppers.ui.screens.GamePage
import com.example.pixelpeppers.ui.screens.MainMenu
import com.example.pixelpeppers.ui.screens.Onboarding
import com.example.pixelpeppers.ui.screens.Search
import com.example.pixelpeppers.ui.screens.Login

@Composable
fun NavGraph(
    startDestination: String,
) {
    val navController = rememberNavController()
    val navigateGame: (Game) -> Unit =
        { game: Game -> navController.navigate(route = "${Route.Game.route}/${game.id}") }
    NavHost(navController = navController, startDestination = startDestination) {
        composable(
            route = Route.OnboardingIntro.route
        ) {
            Onboarding(page = 0, navController = navController)
        }
        composable(
            route = Route.OnboardingTags.route
        ) {
            Onboarding(page = 1, navController = navController)
        }
        composable(
            route = Route.Menu.route
        ) {
            MainMenu(
                onGameClick = navigateGame,
                onSearchClick = { navController.navigate(route = Route.Search.route) },
                onAccountClick = { navController.navigate(route = Route.Login.route) },
                returnToOnboarding= { navController.navigate(route=Route.OnboardingIntro.route)}
            )
        }
        composable(
            route = Route.Login.route
        ) {
            Login(navController = navController)
        }
        composable(
            route = Route.Search.route
        ) {
            Search(onGameClick = navigateGame)
        }
        composable(
            route = "${Route.Game.route}/{gameID}"
        ) {
            GamePage(
                gameID = it.arguments?.getString("gameID")?.toInt() ?: 17000,
            )
        }
    }
}