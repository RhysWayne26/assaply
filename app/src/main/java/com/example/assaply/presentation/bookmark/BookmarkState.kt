package com.example.assaply.presentation.bookmark

import com.example.assaply.data.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)