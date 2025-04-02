package com.example.assaply.presentation.navigation


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.assaply.data.domain.entities.Article
import com.example.assaply.presentation.navigation.components.ButtonNavigationItem
import com.example.assaply.presentation.navigation.components.NewsBottomNavigation
import com.example.assaply.presentation.navigation.components.Route
import com.example.assaply.presentation.screens.BookmarkScreen
import com.example.assaply.presentation.screens.DetailsScreen
import com.example.assaply.presentation.screens.HomeScreen
import com.example.assaply.presentation.screens.SearchScreen
import com.example.assaply.presentation.viewmodels.BookmarkViewModel
import com.example.assaply.presentation.viewmodels.DetailsViewModel
import com.example.assaply.presentation.viewmodels.HomeViewModel
import com.example.assaply.presentation.viewmodels.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsNavigator() {
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value

    val bottomNavigationItems = remember {
        listOf(
            ButtonNavigationItem(icon = R.drawable.ic_home, text = ""),
            ButtonNavigationItem(icon = R.drawable.ic_search, text = ""),
            ButtonNavigationItem(icon = R.drawable.ic_bookmark, text = "")
        )
    }

    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    selectedItem = when (backStackState?.destination?.route) {
        Route.HOME_SCREEN -> 0
        Route.SEARCH_SCREEN -> 1
        Route.BOOKMARK_SCREEN -> 2
        else -> selectedItem
    }

    val isBottomBarVisible = remember(backStackState) {
        backStackState?.destination?.route in listOf(
            Route.HOME_SCREEN,
            Route.SEARCH_SCREEN,
            Route.BOOKMARK_SCREEN
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
                            0 -> navigateToTab(navController, Route.HOME_SCREEN)
                            1 -> navigateToTab(navController, Route.SEARCH_SCREEN)
                            2 -> navigateToTab(navController, Route.BOOKMARK_SCREEN)
                        }
                    }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Route.HOME_SCREEN,
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding())
        ) {
            composable(Route.HOME_SCREEN) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems<Article>()
                HomeScreen(
                    articles = articles,
                    navigateToDetails = { article: Article ->
                        navigateToDetails(navController, article)
                    }
                )
            }

            composable(Route.SEARCH_SCREEN) {
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

            composable(Route.BOOKMARK_SCREEN) {
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

            composable(Route.DETAILS_SCREEN) {
                val viewModel: DetailsViewModel = hiltViewModel()
                val backStackEntry = remember {
                    navController.getBackStackEntry(Route.DETAILS_SCREEN)
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
        navigateToTab(navController, Route.HOME_SCREEN)
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
    navController.navigate(Route.DETAILS_SCREEN) {
        launchSingleTop = true
    }

    navController
        .getBackStackEntry(Route.DETAILS_SCREEN)
        .savedStateHandle["article"] = article
}
