package com.example.assaply.presentation.states

import com.example.assaply.data.domain.entities.Article

data class SearchState(
    val searchQuery: String = "",
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)