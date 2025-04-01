package com.example.assaply.presentation.states

import androidx.paging.PagingData
import com.example.assaply.data.domain.entities.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
)