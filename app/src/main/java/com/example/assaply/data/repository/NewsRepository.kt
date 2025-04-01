package com.example.assaply.data.repository

import androidx.paging.PagingData
import com.example.assaply.data.domain.entities.Article

import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>
    fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>>
    suspend fun upsertArticle(article: Article)
    suspend fun deleteArticle(article: Article)
    fun getArticles(): Flow<List<Article>>
    suspend fun getArticle(url: String): Article?
}