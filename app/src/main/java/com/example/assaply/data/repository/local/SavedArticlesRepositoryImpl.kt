package com.example.assaply.data.repository.local

import com.example.assaply.data.domain.entities.Article
import com.example.assaply.data.room.NewsDao
import kotlinx.coroutines.flow.Flow

class SavedArticlesRepositoryImpl(
    private val dao: NewsDao
) : SavedArticlesRepository {
    override suspend fun upsertArticle(article: Article) = dao.upsert(article)
    override suspend fun deleteArticle(article: Article) = dao.delete(article)
    override fun getArticles(): Flow<List<Article>> = dao.getArticles()
    override suspend fun getArticle(url: String): Article? = dao.getArticle(url)
}
