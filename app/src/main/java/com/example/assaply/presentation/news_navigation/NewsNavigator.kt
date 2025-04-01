package com.example.assaply.presentation.news_navigation


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsNavigator() {
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value

    val bottomNavigationItems = remember {
        listOf(
            ButtonNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            ButtonNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            ButtonNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
        )
    }

    var selectedItem by rememberSaveable { mutableStateOf(0) }

    selectedItem = when (backStackState?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.BookmarkScreen.route -> 2
        else -> selectedItem
    }

    val isBottomBarVisible = remember(backStackState) {
        backStackState?.destination?.route in listOf(
            Route.HomeScreen.route,
            Route.SearchScreen.route,
            Route.BookmarkScreen.route
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
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
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding())
        ) {
            composable(Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToDetails = { article ->
                        navigateToDetails(navController, article)
                    }
                )
            }

            composable(Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState().value
                OnBackClickStateSaver(navController)
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(navController, article)
                    }
                )
            }

            composable(Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController)
                BookmarkScreen(
                    state = state,
                    navigateToDetails = { article ->
                        navigateToDetails(navController, article)
                    }
                )
            }

            composable(Route.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                val backStackEntry = remember {
                    navController.getBackStackEntry(Route.DetailsScreen.route)
                }
                val article = backStackEntry.savedStateHandle.get<Article>("article")

                article?.let {
                    DetailsScreen(
                        article = it,
                        event = viewModel::onEvent,
                    )
                }
            }
        }
    }
}

@Composable
fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateToTab(navController, Route.HomeScreen.route)
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) { saveState = true }
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.navigate(Route.DetailsScreen.route) {
        launchSingleTop = true
    }

    navController
        .getBackStackEntry(Route.DetailsScreen.route)
        .savedStateHandle["article"] = article
}
