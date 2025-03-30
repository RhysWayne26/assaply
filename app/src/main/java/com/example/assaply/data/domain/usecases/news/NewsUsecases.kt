package com.example.assaply.data.domain.usecases.news

data class NewsUsecases(
    val getNews: GetNews,
    val selectArticles: SelectArticles,
    val selectArticle: SelectArticle,
    val upsertArticle: UpsertArticle,
    val deleteArticle: DeleteArticle,
    val searchNews: SearchNews
)
