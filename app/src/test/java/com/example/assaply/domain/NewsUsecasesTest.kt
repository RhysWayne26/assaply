package com.example.assaply.domain

import androidx.paging.PagingData
import com.example.assaply.data.domain.NewsUsecases
import com.example.assaply.data.domain.entities.Article
import com.example.assaply.data.domain.entities.Source
import com.example.assaply.data.repository.local.SavedArticlesRepository
import com.example.assaply.data.repository.remote.RemoteNewsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NewsUsecasesTest {

    private lateinit var usecases: NewsUsecases
    private val remoteRepo = mockk<RemoteNewsRepository>(relaxed = true)
    private val savedRepo = mockk<SavedArticlesRepository>(relaxed = true)

    private val article = Article(
        title = "Test",
        description = "Desc",
        url = "url",
        author = "Author",
        content = "Content",
        publishedAt = "2025-01-01",
        source = Source("id", "name"),
        urlToImage = "img"
    )

    @Before
    fun setup() {
        usecases = NewsUsecases(remoteRepo, savedRepo)
    }

    @Test
    fun `getNews calls remote repository`() = runTest {
        val flow = flowOf(PagingData.from(listOf(article)))
        coEvery { remoteRepo.getNews(any()) } returns flow

        val result = usecases.getNews(listOf("bbc"))
        assertEquals(flow, result)
    }

    @Test
    fun `searchNews calls remote repository`() = runTest {
        val flow = flowOf(PagingData.from(listOf(article)))
        coEvery { remoteRepo.searchNews(any(), any()) } returns flow

        val result = usecases.searchNews("kotlin", listOf("bbc"))
        assertEquals(flow, result)
    }

    @Test
    fun `getSavedArticles calls saved repository`() = runTest {
        val flow = flowOf(listOf(article))
        coEvery { savedRepo.getArticles() } returns flow

        val result = usecases.getSavedArticles()
        assertEquals(flow, result)
    }

    @Test
    fun `getArticle calls saved repository`() = runTest {
        coEvery { savedRepo.getArticle("url") } returns article

        val result = usecases.getArticle("url")
        assertEquals(article, result)
    }

    @Test
    fun `upsertArticle calls saved repository`() = runTest {
        usecases.upsertArticle(article)
        coVerify { savedRepo.upsertArticle(article) }
    }

    @Test
    fun `deleteArticle calls saved repository`() = runTest {
        usecases.deleteArticle(article)
        coVerify { savedRepo.deleteArticle(article) }
    }
}
