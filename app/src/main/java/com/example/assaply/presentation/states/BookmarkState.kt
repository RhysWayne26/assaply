package com.example.assaply.presentation.states

import com.example.assaply.data.domain.entities.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)