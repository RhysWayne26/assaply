package com.example.assaply.repo

import androidx.paging.PagingData
import com.example.assaply.data.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>
}