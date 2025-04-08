package com.example.assaply.presentation.screens

import ArticlesList
import ShimmerEffect
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.assaply.data.domain.entities.Article
import com.example.assaply.data.domain.entities.Source
import com.example.assaply.presentation.common.SearchBar
import com.example.assaply.presentation.events.SearchEvent
import com.example.assaply.presentation.states.SearchState
import com.example.assaply.util.Dimensions.MediumPadding1
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchScreen(
    state: SearchState,
    articles: LazyPagingItems<Article>,
    event: (SearchEvent) -> Unit,
    navigateToDetails: (Article) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .statusBarsPadding()
            .padding(
                top = MediumPadding1,
                start = MediumPadding1,
                end = MediumPadding1
            )
    ) {
        val (searchBarRef, spacerRef, listRef) = createRefs()

        SearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = { event(SearchEvent.SearchNews) },
            modifier = Modifier.constrainAs(searchBarRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Spacer(
            modifier = Modifier
                .height(MediumPadding1)
                .constrainAs(spacerRef) {
                    top.linkTo(searchBarRef.bottom)
                }
        )

        val isLoading = articles.loadState.refresh is LoadState.Loading

        if (isLoading) {
            ShimmerEffect(
                modifier = Modifier.constrainAs(listRef) {
                    top.linkTo(spacerRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        } else {
            ArticlesList(
                articles = articles,
                onClick = navigateToDetails,
                modifier = Modifier.constrainAs(listRef) {
                    top.linkTo(spacerRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    val mockArticles = listOf(
        Article(
            title = "Mock News 1",
            content = "Content of mock news 1",
            author = "Author 1",
            description = "Description 1",
            publishedAt = "2025-04-08T10:00:00Z",
            source = Source("1", "Mock Source"),
            url = "https://example.com/mock1",
            urlToImage = ""
        ),
        Article(
            title = "Mock News 2",
            content = "Content of mock news 2",
            author = "Author 2",
            description = "Description 2",
            publishedAt = "2025-04-08T11:00:00Z",
            source = Source("2", "Mock Source 2"),
            url = "https://example.com/mock2",
            urlToImage = ""
        )
    )

    val mockPagingData = PagingData.from(mockArticles)
    val lazyPagingItems = flowOf(mockPagingData).collectAsLazyPagingItems()

    SearchScreen(
        state = SearchState(searchQuery = "mock"),
        articles = lazyPagingItems,
        event = {},
        navigateToDetails = {}
    )
}
