package com.example.assaply.presentation.screens

import ArticlesList
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.assaply.data.domain.entities.Article
import com.example.assaply.presentation.states.BookmarkState
import com.example.assaply.util.Dimensions.MediumPadding1

@Composable
fun BookmarkScreen(
    state: BookmarkState,
    navigateToDetails:(Article)->Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = MediumPadding1, start = MediumPadding1, end = MediumPadding1)

    ){
        Spacer(
            modifier = Modifier.height(MediumPadding1)
        )
        ArticlesList(articles = state.articles, onClick = {article -> navigateToDetails(article)})
    }
}