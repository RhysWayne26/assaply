package com.example.assaply.data.domain.usecases.news

import androidx.paging.PagingData
import com.example.assaply.data.domain.model.Article
import com.example.assaply.repo.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
){
    operator fun invoke(sources: List<String>): Flow<PagingData<Article>>{
        return newsRepository.getNews(sources = sources)
    }
}

