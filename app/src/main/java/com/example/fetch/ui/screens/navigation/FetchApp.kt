package com.example.fetch.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fetch.ui.screens.feed.UserFeedScreen
import com.example.fetch.ui.screens.feed.UserFeedViewModel

@Composable
fun FetchApp() {
    val navController = rememberNavController()
    val viewModel =  hiltViewModel<UserFeedViewModel>()

    NavHost(navController = navController, startDestination = Screen.UserFeedScreen.route) {
        composable(
            route = Screen.UserFeedScreen.route){
            UserFeedScreen(viewModel)
        }
    }
}