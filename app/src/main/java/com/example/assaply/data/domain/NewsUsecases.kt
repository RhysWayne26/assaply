package com.example.assaply.data.domain

import com.example.assaply.data.domain.entities.Article
import com.example.assaply.data.repository.NewsRepository
import com.example.assaply.data.room.NewsDao

class NewsUsecases(
    private val repository: NewsRepository,
    private val dao: NewsDao
) {
    fun getNews(sources: List<String>) = repository.getNews(sources)
    fun searchNews(query: String, sources: List<String>) = repository.searchNews(query, sources)
    fun getSavedArticles() = dao.getArticles()
    suspend fun getArticle(url: String) = dao.getArticle(url)
    suspend fun upsertArticle(article: Article) = dao.upsert(article)
    suspend fun deleteArticle(article: Article) = dao.delete(article)
}