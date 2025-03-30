package com.example.assaply.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.assaply.presentation.bookmark.BookmarkScreen
import com.example.assaply.presentation.bookmark.BookmarkViewModel
import com.example.assaply.presentation.home.HomeViewModel
import com.example.assaply.presentation.welcome.WelcomeScreen
import com.example.assaply.presentation.welcome.WelcomeViewModel

@Composable
fun NavGraph(startDestination: String){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination){
        navigation(
            route = Route.AppStartNav.route,
            startDestination = Route.WelcomeScreen.route
        ){
            composable(
                route = Route.WelcomeScreen.route
            ){
                val viewModel: WelcomeViewModel = hiltViewModel()
                WelcomeScreen (
                    event = viewModel::onEvent
                )
            }
        }
        navigation(
            route = Route.NewsNav.route,
            startDestination = Route.NewsNavScreen.route
        ){
            composable(route=Route.NewsNavScreen.route){
                val viewModel: HomeViewModel = hiltViewModel()

            }
        }
        navigation(
            route = Route.NewsNav.route,
            startDestination = Route.NewsNavScreen.route
        ){
            composable(route=Route.NewsNavScreen.route){
                val viewModel: BookmarkViewModel = hiltViewModel()
                BookmarkScreen(state = viewModel.state.value, navigate = {})
            }
        }
    }
}