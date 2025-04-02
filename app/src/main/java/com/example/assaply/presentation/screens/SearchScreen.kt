package com.example.assaply.presentation.screens

import ArticlesList
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.assaply.data.domain.entities.Article
import com.example.assaply.presentation.common.SearchBar
import com.example.assaply.presentation.events.SearchEvent
import com.example.assaply.presentation.states.SearchState
import com.example.assaply.util.Dimensions.MediumPadding1


@Composable
fun SearchScreen(
    state: SearchState,
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

        androidx.compose.foundation.layout.Spacer(
            modifier = Modifier
                .height(MediumPadding1)
                .constrainAs(spacerRef) {
                    top.linkTo(searchBarRef.bottom)
                }
        )

        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
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
    val mockState = SearchState(
        searchQuery = "mock news",
        articles = null
    )

    SearchScreen(
        state = mockState,
        event = {},
        navigateToDetails = {}
    )
}
