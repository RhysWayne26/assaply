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
import androidx.compose.ui.tooling.preview.Preview
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

@Preview(showBackground = true)
@Composable
fun BookmarkScreenPreview() {
    val mockArticles = listOf(
        Article(
            author = "John Doe",
            content = "This is a preview content for article one.",
            description = "Short description of article one.",
            publishedAt = "2025-04-01T12:00:00Z",
            source = com.example.assaply.data.domain.entities.Source(id = "1", name = "Mock Source"),
            title = "First Mock Article",
            url = "https://example.com/article1",
            urlToImage = ""
        ),
        Article(
            author = "Jane Smith",
            content = "This is a preview content for article two.",
            description = "Short description of article two.",
            publishedAt = "2025-04-01T14:30:00Z",
            source = com.example.assaply.data.domain.entities.Source(id = "2", name = "Another Source"),
            title = "Second Mock Article",
            url = "https://example.com/article2",
            urlToImage = ""
        )
    )

    BookmarkScreen(
        state = BookmarkState(articles = mockArticles),
        navigateToDetails = {}
    )
}
