package com.example.assaply.data.repository.local

import com.example.assaply.data.domain.entities.Article
import kotlinx.coroutines.flow.Flow

interface SavedArticlesRepository{
    suspend fun upsertArticle(article: Article)
    suspend fun deleteArticle(article: Article)
    fun getArticles(): Flow<List<Article>>
    suspend fun getArticle(url: String): Article?
}