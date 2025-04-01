package com.example.assaply.data.api

import com.example.assaply.data.domain.entities.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)