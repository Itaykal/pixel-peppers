package com.example.pixelpeppers

sealed class Route(val route: String) {
    object OnboardingIntro: Route(route = "onboardingIntro")
    object OnboardingTags: Route(route = "OnboardingTags")
    object Login: Route(route = "login")
    object Menu: Route(route = "menu")
    object Search: Route(route = "search")
    object Account: Route(route = "account")
    object Game: Route(route = "game")
}