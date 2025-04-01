package com.example.assaply.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.assaply.data.domain.entities.Article


class NewsPagingSource(
    private val newsApi: NewsApi,
    private val sources: String,
    private val searchQuery: String? = null
): PagingSource<Int, Article>() {

    private var totalNewsCount =0
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let{
            anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1

        return try {
            val response = newsApi.getNews(page = page, sources = sources, query = searchQuery)

            totalNewsCount = response.articles.size
            val articles = response.articles.distinctBy { article -> article.title }

            LoadResult.Page(
                data = articles,
                prevKey = null,
                nextKey = if (totalNewsCount == response.totalResults) null else page + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

}