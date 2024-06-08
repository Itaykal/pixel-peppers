package com.example.pixelpeppers

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.pixelpeppers.ui.screens.Onboarding
import com.example.pixelpeppers.ui.screens.OnboardingIntro
import com.example.pixelpeppers.ui.screens.OnboardingTags

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
    }
}