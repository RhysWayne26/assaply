package com.example.assaply.repository.local

import com.example.assaply.data.domain.entities.Article
import com.example.assaply.data.domain.entities.Source
import com.example.assaply.data.repository.local.SavedArticlesRepositoryImpl
import com.example.assaply.data.room.NewsDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SavedArticlesRepositoryImplTest {

    private val dao = mockk<NewsDao>(relaxed = true)
    private val repository = SavedArticlesRepositoryImpl(dao)

    private val article = Article(
        title = "Saved Article",
        description = "Description",
        url = "https://example.com",
        author = "Author",
        content = "Content",
        publishedAt = "2025-01-01",
        source = Source("id", "name"),
        urlToImage = "image_url"
    )

    /**
     * Проверяет, что метод `getArticles()` в репозитории возвращает статьи, предоставленные DAO.
     * Мокаем возвращаемое значение `dao.getArticles()` как Flow со списком из одной статьи.
     * Затем проверяем, что результат действительно содержит нужную статью.
     */
    @Test
    fun `getArticles returns articles from dao`() = runTest {
        coEvery { dao.getArticles() } returns flowOf(listOf(article))

        val result = repository.getArticles()
        assertEquals("Saved Article", result.first().first().title)
    }

    /**
     * Проверяет, что метод `getArticle()` репозитория делегирует вызов методу DAO.
     * Возвращаем из мока нужную статью и сравниваем результат.
     */
    @Test
    fun `getArticle delegates to dao`() = runTest {
        coEvery { dao.getArticle(article.url) } returns article

        val result = repository.getArticle(article.url)
        assertEquals(article, result)
    }

    /**
     * Проверяет, что метод `upsertArticle()` вызывает соответствующий метод в DAO.
     * Используем `coVerify` для проверки вызова.
     */
    @Test
    fun `upsertArticle calls dao upsert`() = runTest {
        repository.upsertArticle(article)
        coVerify { dao.upsert(article) }
    }

    /**
     * Проверяет, что метод `deleteArticle()` вызывает соответствующий метод в DAO.
     * Аналогично используем `coVerify`.
     */
    @Test
    fun `deleteArticle calls dao delete`() = runTest {
        repository.deleteArticle(article)
        coVerify { dao.delete(article) }
    }
}
