package com.example.assaply.data.domain

import com.example.assaply.data.domain.entities.Article
import com.example.assaply.data.repository.NewsRepository

class NewsUsecases(
    private val repository: NewsRepository,
) {
    fun getNews(sources: List<String>) = repository.getNews(sources)
    fun searchNews(query: String, sources: List<String>) = repository.searchNews(query, sources)
    fun getSavedArticles() = repository.getArticles()
    suspend fun getArticle(url: String) = repository.getArticle(url)
    suspend fun upsertArticle(article: Article) = repository.upsertArticle(article)
    suspend fun deleteArticle(article: Article) = repository.deleteArticle(article)
}