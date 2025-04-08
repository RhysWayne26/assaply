package com.example.assaply.data.repository.remote

import androidx.paging.PagingData
import com.example.assaply.data.domain.entities.Article
import kotlinx.coroutines.flow.Flow

interface RemoteNewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>
    fun searchNews(query: String, sources: List<String>): Flow<PagingData<Article>>
}
