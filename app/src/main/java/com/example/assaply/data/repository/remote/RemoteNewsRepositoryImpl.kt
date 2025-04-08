package com.example.assaply.data.repository.remote

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.assaply.data.api.NewsApi
import com.example.assaply.data.api.NewsPagingSource
import com.example.assaply.data.domain.entities.Article
import kotlinx.coroutines.flow.Flow

class RemoteNewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val context: Context
) : RemoteNewsRepository{

    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        val language = context.resources.configuration.locales[0].language
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(newsApi, sources.joinToString(","))
            }
        ).flow
    }

    override fun searchNews(query: String, sources: List<String>): Flow<PagingData<Article>> {
        val language = context.resources.configuration.locales[0].language
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(newsApi, sources.joinToString(","), query)
            }
        ).flow
    }
}
