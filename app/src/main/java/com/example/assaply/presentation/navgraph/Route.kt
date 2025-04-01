package com.example.assaply.presentation.navgraph

sealed class Route(
    val route: String
) {
    object WelcomeScreen : Route(route = "welcomeScreen")
    object HomeScreen : Route(route = "homeScreen")
    object SearchScreen : Route(route = "searchScreen")
    object DetailsScreen : Route(route = "detailsScreen")
    object BookmarkScreen : Route(route = "bookmarkScreen")
    object AppStartNav : Route(route = "appStartNav")
    object NewsNav : Route(route = "newsNav")
    object NewsNavScreen : Route(route = "newsNavScreen")

}