package com.example.pixelpeppers

sealed class Route(val route: String) {
    object OnboardingIntro: Route(route = "onboardingIntro")
    object OnboardingTags: Route(route = "OnboardingTags")
    object Login: Route(route = "login")
}