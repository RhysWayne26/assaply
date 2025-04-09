package com.example.assaply.viewmodels

import com.example.assaply.data.domain.NewsUsecases
import com.example.assaply.data.domain.entities.Article
import com.example.assaply.data.domain.entities.Source
import com.example.assaply.presentation.viewmodels.BookmarkViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookmarkViewModelTest {

    private lateinit var viewModel: BookmarkViewModel
    private lateinit var newsUsecases: NewsUsecases

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        newsUsecases = mockk()
        val fakeArticles = listOf(
            Article(
                title = "Test 1", description = "Desc 1", url = "url1",
                author = "rooster",
                content = "shock content 1",
                publishedAt = "4am",
                source = Source(
                    id = "1",
                    name = "you mom",
                ),
                urlToImage = ""
            ),
            Article(
                title = "Test 2", description = "Desc 2", url = "url2",
                author = "rooster",
                content = "shock content 2",
                publishedAt = "4am",
                source = Source(
                    id = "2",
                    name = "you mom",
                ),
                urlToImage = ""
            ),
        )
        coEvery { newsUsecases.getSavedArticles() } returns flowOf(fakeArticles)
        viewModel = BookmarkViewModel(newsUsecases)
    }

    @Test
    fun `BookmarkViewModel loads saved articles on init`() = runTest {
        val expectedTitles = listOf("Test 1", "Test 2")
        val actualTitles = viewModel.state.value.articles.map { it.title }

        assertEquals(expectedTitles, actualTitles)
    }

    @Test
    fun `BookmarkViewModel handles empty list`() = runTest {
        coEvery { newsUsecases.getSavedArticles() } returns flowOf(emptyList())
        viewModel = BookmarkViewModel(newsUsecases)

        assertEquals(0, viewModel.state.value.articles.size)
    }

    @Test
    fun `BookmarkViewModel returns correct number of articles`() = runTest {
        assertEquals(2, viewModel.state.value.articles.size)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}


