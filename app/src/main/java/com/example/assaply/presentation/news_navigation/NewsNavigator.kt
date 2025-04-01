package com.example.assaply.presentation.news_navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.assaply.R
import com.example.assaply.data.domain.model.Article
import com.example.assaply.presentation.bookmark.BookmarkScreen
import com.example.assaply.presentation.bookmark.BookmarkViewModel
import com.example.assaply.presentation.details.DetailsScreen
import com.example.assaply.presentation.details.DetailsViewModel
import com.example.assaply.presentation.home.HomeScreen
import com.example.assaply.presentation.home.HomeViewModel
import com.example.assaply.presentation.navgraph.Route
import com.example.assaply.presentation.news_navigation.components.ButtonNavigationItem
import com.example.assaply.presentation.news_navigation.components.NewsBottomNavigation
import com.example.assaply.presentation.search.SearchScreen
import com.example.assaply.presentation.search.SearchViewModel

@Composable
fun NewsNavigator() {
    val bottomNavigationItems = remember {
        listOf(
            ButtonNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            ButtonNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            ButtonNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
        )
    }

    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    val selectedItem = when (backstackState?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.BookmarkScreen.route -> 2
        else -> 0
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NewsBottomNavigation(
                items = bottomNavigationItems,
                selected = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(navController, Route.HomeScreen.route)
                        1 -> navigateToTab(navController, Route.SearchScreen.route)
                        2 -> navigateToTab(navController, Route.BookmarkScreen.route)
                    }
                }
            )
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ){
            composable(route = Route.HomeScreen.route){
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route)
                    },
                    navigateToDetails = {
                        article -> navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }
            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = {
                        navigateToDetails(
                            navController = navController,
                            article = it
                        )
                    }
                )
            }

            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(state = state, navigateToDetails = {
                    article -> navigateToDetails(navController = navController, article = article)
                })

            }

            composable(route = Route.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                navController
                    .previousBackStackEntry?.savedStateHandle?.get<Article?>("article")?.let{
                        article -> DetailsScreen(article = article, event = viewModel::onEvent, navigateUp = {
                            navController.navigateUp() })
                    }
            }
        }
    }
}



private fun navigateToTab(navController: NavController, route: String){
    navController.navigate(route){
        navController.graph.startDestinationRoute?.let{
            homeScreen -> popUpTo(homeScreen)
        }
        restoreState = true
        launchSingleTop = true
    }
}

private fun navigateToDetails(navController: NavController, article: Article){
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
}