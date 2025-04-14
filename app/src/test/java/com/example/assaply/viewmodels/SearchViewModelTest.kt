package com.example.assaply.viewmodels

import com.example.assaply.data.domain.NewsUsecases
import com.example.assaply.data.domain.entities.Article
import com.example.assaply.data.domain.entities.Source
import com.example.assaply.presentation.events.SearchEvent
import com.example.assaply.presentation.viewmodels.SearchViewModel
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
import androidx.paging.PagingData

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var newsUsecases: NewsUsecases
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        // Устанавливаем тестовый диспетчер для корутин
        Dispatchers.setMain(testDispatcher)
        // Мокаем зависимость usecases
        newsUsecases = mockk()
        // Инициализируем ViewModel с моком
        viewModel = SearchViewModel(newsUsecases)
    }

    @Test
    fun `onEvent UpdateSearchQuery updates searchQuery in state`() = runTest {
        // Передаём новое значение поискового запроса
        val query = "kotlin"
        viewModel.onEvent(SearchEvent.UpdateSearchQuery(query))

        // Проверяем, что состояние ViewModel обновилось
        val state = viewModel.state.value
        assertEquals(query, state.searchQuery)
    }

    @Test
    fun `onEvent SearchNews updates state with articles`() = runTest {
        // Подготавливаем фейковую статью
        val article = Article(
            title = "Title",
            description = "Desc",
            url = "url",
            author = "Author",
            content = "Some content",
            publishedAt = "2025-01-01",
            source = Source("id", "source name"),
            urlToImage = "image_url"
        )

        // Оборачиваем статью в PagingData
        val pagingData = PagingData.from(listOf(article))

        // Мокаем usecase на возврат этого PagingData
        coEvery { newsUsecases.searchNews(any(), any()) } returns flowOf(pagingData)

        // Обновляем поисковый запрос и запускаем поиск
        viewModel.onEvent(SearchEvent.UpdateSearchQuery("kotlin"))
        viewModel.onEvent(SearchEvent.SearchNews)

        // Даём немного времени для сбора данных
        kotlinx.coroutines.delay(200)

        // Проверка: список статей содержит нужную статью
        val state = viewModel.state.value
        assertEquals(1, state.articles.size)
        assertEquals("Title", state.articles[0].title)
    }

    @After
    fun tearDown() {
        // Сбрасываем диспетчер после тестов
        Dispatchers.resetMain()
    }
}
