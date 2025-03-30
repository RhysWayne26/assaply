package com.example.assaply.presentation.news_navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.assaply.R
import com.example.assaply.presentation.news_navigation.components.ButtonNavigationItem

@Composable
fun NewsNavigator (){
    val bottomNavigationItems = remember{
        listOf(
            ButtonNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            ButtonNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            ButtonNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
        )
    }
    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
}