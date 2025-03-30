package com.example.assaply.presentation.home

//noinspection SuspiciousImport
import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.assaply.data.domain.model.Article
import com.example.assaply.presentation.Dimensions.MediumPadding1
import com.example.assaply.presentation.common.ArticleCard
import com.example.assaply.presentation.common.ArticlesList
import com.example.assaply.presentation.common.SearchBar
import com.example.assaply.presentation.navgraph.Route

@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    navigate: (String) -> Unit
) {
    val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList.items
                    .take(10)
                    .joinToString(separator = " \uD83D\uDFE5 ") { it.title }
            } else ""
        }
    }

    val searchText = remember { "" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MediumPadding1)
            .statusBarsPadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_menu_gallery),
            contentDescription = null,
            modifier = Modifier.padding(horizontal = MediumPadding1)
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        SearchBar(
            text = searchText,
            readOnly = true,
            onValueChange = {},
            onClick = {
                navigate(Route.SearchScreen.route)
            },
            onSearch = {}
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        ArticlesList(
            modifier = Modifier.padding(horizontal = MediumPadding1),
            articles = articles.itemSnapshotList.items,
            onClick = {
                navigate(Route.DetailsScreen.route)
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
            publishedAt = "2025-03-26T12:00:00Z",
            source = com.example.assaply.data.domain.model.Source("1", "Mock Source"),
            url = "https://example.com/news1",
            urlToImage = ""
        ),
        Article(
            title = "Breaking News 2",
            content = "Content 2",
            author = "Author B",
            description = "Short description 2",
            publishedAt = "2025-03-25T10:30:00Z",
            source = com.example.assaply.data.domain.model.Source("2", "Mock Source 2"),
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

