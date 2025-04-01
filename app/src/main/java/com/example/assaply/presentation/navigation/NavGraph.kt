package com.example.assaply.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.assaply.presentation.navigation.components.Route
import com.example.assaply.presentation.screens.WelcomeScreen
import com.example.assaply.presentation.viewmodels.WelcomeViewModel

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

        navigation(
            route = Route.APP_START_NAV,
            startDestination = Route.WELCOME_SCREEN
        ) {
            composable(route = Route.WELCOME_SCREEN) {
                val viewModel: WelcomeViewModel = hiltViewModel()
                WelcomeScreen(
                    navController = navController,
                    event = viewModel::onEvent
                )
            }
        }

        navigation(
            route = Route.NEWS_NAV,
            startDestination = Route.NEWS_NAV_SCREEN
        ) {
            composable(route = Route.NEWS_NAV_SCREEN) {
                NewsNavigator()
            }
        }
    }
}
