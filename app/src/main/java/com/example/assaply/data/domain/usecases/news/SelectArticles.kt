package com.example.assaply.data.domain.usecases.news

import com.example.assaply.data.domain.model.Article
import com.example.assaply.data.local.NewsDao
import kotlinx.coroutines.flow.Flow

class SelectArticles(
    private val newsDao: NewsDao
){
    operator fun invoke(): Flow<List<Article>>{
        return newsDao.getArticles()
    }
}