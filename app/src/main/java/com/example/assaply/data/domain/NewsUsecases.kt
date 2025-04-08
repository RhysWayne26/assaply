package com.example.assaply.data.domain

import com.example.assaply.data.domain.entities.Article
import com.example.assaply.data.repository.local.SavedArticlesRepository
import com.example.assaply.data.repository.remote.RemoteNewsRepository

class NewsUsecases(
    private val remoteNewsRepository: RemoteNewsRepository,
    private val savedArticlesRepository: SavedArticlesRepository
) {
    fun getNews(sources: List<String>) = remoteNewsRepository.getNews(sources)
    fun searchNews(query: String, sources: List<String>) = remoteNewsRepository.searchNews(query, sources)
    fun getSavedArticles() = savedArticlesRepository.getArticles()
    suspend fun getArticle(url: String) = savedArticlesRepository.getArticle(url)
    suspend fun upsertArticle(article: Article) = savedArticlesRepository.upsertArticle(article)
    suspend fun deleteArticle(article: Article) = savedArticlesRepository.deleteArticle(article)
}