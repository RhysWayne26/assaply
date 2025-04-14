package com.example.assaply.api

import androidx.paging.PagingSource.LoadParams.Refresh
import androidx.paging.PagingSource.LoadResult
import com.example.assaply.data.api.NewsApi
import com.example.assaply.data.api.NewsPagingSource
import com.example.assaply.data.api.NewsResponse
import com.example.assaply.data.domain.entities.Article
import com.example.assaply.data.domain.entities.Source
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsPagingSourceTest {

    private val newsApi = mockk<NewsApi>()

    // Подготовленные статьи с одинаковым title, чтобы проверить фильтрацию дубликатов
    private val duplicateArticles = listOf(
        Article(
            title = "Same Title",
            description = "Desc 1",
            url = "url1",
            author = "Author",
            content = "Content",
            publishedAt = "2025-01-01",
            source = Source("1", "Source 1"),
            urlToImage = "image"
        ),
        Article(
            title = "Same Title", // дубликат по title
            description = "Desc 2",
            url = "url2",
            author = "Author 2",
            content = "Content 2",
            publishedAt = "2025-01-02",
            source = Source("2", "Source 2"),
            urlToImage = "image2"
        )
    )

    /**
     * Тест: проверяет, что PagingSource корректно фильтрует дубликаты по заголовку.
     *
     * Действия:
     * - эмулируется ответ от API с двумя статьями с одинаковым title;
     * - PagingSource должен вернуть только одну уникальную статью.
     *
     * Ожидания:
     * - результат типа LoadResult.Page;
     * - размер списка данных — 1;
     * - заголовок первой статьи — "Same Title";
     * - prevKey = null (первая страница);
     * - nextKey = 2 (следующая страница).
     */
    @Test
    fun `load returns LoadResult_Page with distinct articles`() = runTest {
        coEvery {
            newsApi.getNews(page = 1, sources = "bbc", query = "kotlin")
        } returns NewsResponse(
            articles = duplicateArticles,
            status = "ok",
            totalResults = 1
        )

        val pagingSource = NewsPagingSource(newsApi, sources = "bbc", searchQuery = "kotlin")

        val result = pagingSource.load(
            Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is LoadResult.Page)
        val page = result as LoadResult.Page

        assertEquals(1, page.data.size)
        assertEquals("Same Title", page.data.first().title)
        assertEquals(null, page.prevKey)
        assertEquals(2, page.nextKey)
    }

    /**
     * Тест: проверяет, что при исключении от API возвращается LoadResult.Error.
     *
     * Действия:
     * - имитируется исключение (например, "Network error") при вызове API;
     * - PagingSource должен вернуть ошибку.
     *
     * Ожидания:
     * - результат типа LoadResult.Error;
     * - сообщение ошибки соответствует ожиданию.
     */
    @Test
    fun `load returns LoadResult_Error on failure`() = runTest {
        coEvery {
            newsApi.getNews(any(), any(), any())
        } throws RuntimeException("Network error")

        val pagingSource = NewsPagingSource(newsApi, sources = "cnn", searchQuery = null)

        val result = pagingSource.load(
            Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is LoadResult.Error)
        val error = result as LoadResult.Error
        assertEquals("Network error", error.throwable.message)
    }
}
