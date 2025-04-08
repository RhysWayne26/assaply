package com.example.assaply.presentation.screens

//noinspection SuspiciousImport


import ArticlesList
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.assaply.R
import com.example.assaply.data.domain.entities.Article
import com.example.assaply.data.domain.entities.Source
import com.example.assaply.presentation.common.ArticleCard
import com.example.assaply.util.Dimensions.MediumPadding1

@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    navigateToDetails: (Article) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MediumPadding1)
            .statusBarsPadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.padding(horizontal = MediumPadding1),
            colorFilter = ColorFilter.tint(colorResource(id = R.color.android_emblem))
        )

        ArticlesList(
            modifier = Modifier.padding(horizontal = MediumPadding1),
            articles = articles.itemSnapshotList.items,
            onClick = {
                navigateToDetails(it)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val mockArticles = listOf(
        Article(
            title = "Breaking News 1",
            content = "Content 1",
            author = "Author A",
            description = "Short description 1",
            publishedAt = "woo woo",
            source = Source("1", "Mock Source"),
            url = "https://example.com/news1",
            urlToImage = ""
        ),
        Article(
            title = "Breaking News 2",
            content = "Content 2",
            author = "Author B",
            description = "Short description 2",
            publishedAt = "woo woo",
            source = Source("2", "Mock Source 2"),
            url = "https://example.com/news2",
            urlToImage = ""
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Превью карточек статей:")
        Spacer(modifier = Modifier.height(8.dp))

        mockArticles.forEach { article ->
            ArticleCard(
                article = article,
                onClick = {}
            )
        }
    }
}

