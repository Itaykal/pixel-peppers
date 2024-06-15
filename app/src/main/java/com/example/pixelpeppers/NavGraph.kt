package com.example.pixelpeppers

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pixelpeppers.ui.screens.MainMenu
import com.example.pixelpeppers.ui.screens.Onboarding
import com.example.pixelpeppers.ui.screens.Search
import com.example.pixelpeppers.ui.screens.TempPage

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(
            route = Route.OnboardingIntro.route
        ){
            Onboarding(page = 0, navController = navController)
        }
        composable(
            route = Route.OnboardingTags.route
        ){
            Onboarding(page = 1, navController = navController)
        }
        composable(
            route = Route.Menu.route
        ) {
            MainMenu(navController = navController)
        }
        composable(
            route = Route.Login.route
        ) {
            TempPage(navController = navController)
        }
        composable(
            route = Route.Search.route
        ) {
            Search()
        }
    }
}