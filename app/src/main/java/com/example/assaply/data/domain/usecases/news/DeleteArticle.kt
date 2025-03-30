package com.example.assaply.data.domain.usecases.news

import com.example.assaply.data.domain.model.Article
import com.example.assaply.data.local.NewsDao

class DeleteArticle(
    private val newsDao: NewsDao
){
    suspend operator fun invoke(article: Article){
        newsDao.delete(article)
    }
}