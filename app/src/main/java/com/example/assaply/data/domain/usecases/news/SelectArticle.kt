package com.example.assaply.data.domain.usecases.news

import com.example.assaply.data.domain.model.Article
import com.example.assaply.data.local.NewsDao

class SelectArticle(
    private val newsDao: NewsDao
){
    suspend operator fun invoke(url: String): Article?{
        return newsDao.getArticle(url)
    }
}