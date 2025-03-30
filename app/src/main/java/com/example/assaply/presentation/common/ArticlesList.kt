package com.example.assaply.presentation.common

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.assaply.data.domain.model.Article
import com.example.assaply.presentation.Dimensions.ExtraSmallPadding2
import com.example.assaply.presentation.Dimensions.MediumPadding1

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    val handlePagingResult = handlePagingResult(articles = articles)
    if (handlePagingResult){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MediumPadding1),
            contentPadding = PaddingValues(all = ExtraSmallPadding2)
        ){
            items(count = articles.itemCount){
                articles[it]?.let{
                    ArticleCard(article = it, onClick = { onClick(it)})
                }
            }
        }
    }
}


@Composable
fun handlePagingResult(articles: LazyPagingItems<Article>): Boolean{
    val loadState = articles.loadState
    val error = when{
        loadState.refresh is LoadState.Error ->
            loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error ->
            loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error ->
            loadState.append as LoadState.Error
        else -> null
    }
    return when{
        loadState.refresh is LoadState.Loading ->{
            ShimmerEffect()
            false
        }
        error != null -> {
            EmptyScreen(error = error)
            false
        }
        else -> true
    }
}


@Composable
private fun ShimmerEffect(){
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)){
        repeat(10){
            CardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}

@Composable
fun CardShimmerEffect(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .height(100.dp)
            .shimmerEffect()
    )
}