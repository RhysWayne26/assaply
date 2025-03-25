package com.example.assaply.data.domain.remote.dto

import com.example.assaply.data.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)