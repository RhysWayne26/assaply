package com.example.assaply.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.assaply.data.api.NewsApi
import com.example.assaply.data.api.NewsPagingSource
import com.example.assaply.data.domain.entities.Article
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

@Module
@InstallIn(SingletonComponent::class)
class NewsRepositoryImplementation(
    private val newsApi: NewsApi
) : NewsRepository {

    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(","),
                    searchQuery = searchQuery
                )
            }
        ).flow
    }

    override suspend fun upsertArticle(article: Article) {
        TODO("upsertArticle is not yet implemented")
    }

    override suspend fun deleteArticle(article: Article) {
        TODO("deleteArticle is not yet implemented")
    }

    override fun getArticles(): Flow<List<Article>> {
        TODO("getArticles is not yet implemented")
    }

    override suspend fun getArticle(url: String): Article? {
        TODO("getArticle is not yet implemented")
    }


}