package com.example.fetch.ui.screens.navigation

sealed class Screen(val route: String) {
    data object UserFeedScreen : Screen("UserFeedScreen")
}