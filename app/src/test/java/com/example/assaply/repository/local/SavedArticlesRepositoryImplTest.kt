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

    @Test
    fun `getArticles returns articles from dao`() = runTest {
        coEvery { dao.getArticles() } returns flowOf(listOf(article))

        val result = repository.getArticles()
        assertEquals("Saved Article", result.first().first().title)
    }

    @Test
    fun `getArticle delegates to dao`() = runTest {
        coEvery { dao.getArticle(article.url) } returns article

        val result = repository.getArticle(article.url)
        assertEquals(article, result)
    }

    @Test
    fun `upsertArticle calls dao upsert`() = runTest {
        repository.upsertArticle(article)
        coVerify { dao.upsert(article) }
    }

    @Test
    fun `deleteArticle calls dao delete`() = runTest {
        repository.deleteArticle(article)
        coVerify { dao.delete(article) }
    }
}
