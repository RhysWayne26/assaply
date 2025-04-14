package com.example.assaply.viewmodels

import com.example.assaply.data.domain.NewsUsecases
import com.example.assaply.data.domain.entities.Article
import com.example.assaply.data.domain.entities.Source
import com.example.assaply.presentation.events.DetailsEvent
import com.example.assaply.presentation.viewmodels.DetailsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    // Используем StandardTestDispatcher для управления временем в корутинах
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: DetailsViewModel
    private lateinit var usecases: NewsUsecases

    // Настройка перед каждым тестом
    @Before
    fun setup() {
        // Инициализация моков
        MockKAnnotations.init(this)
        // Подменяем диспетчер Main на testDispatcher
        Dispatchers.setMain(testDispatcher)
        // Создаём мок usecases с relaxed = true, чтобы не указывать поведение всех методов
        usecases = mockk(relaxed = true)
    }

    // Очистка после каждого теста
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `calls upsert when article not found`() = runTest {
        // Подготавливаем статью
        val article = Article(
            url = "url123",
            title = "Title",
            description = "Description",
            content = "Content",
            publishedAt = "2024-01-01",
            source = Source(id = "2", name = "abc"),
            author = "Author",
            urlToImage = ""
        )

        // Имитируем отсутствие статьи в локальном хранилище
        coEvery { usecases.getArticle(article.url) } returns null

        // Инициализация ViewModel
        viewModel = DetailsViewModel(usecases)
        // Отправляем событие сохранения/удаления статьи
        viewModel.onEvent(DetailsEvent.UpsertDeleteArticle(article))
        // Продвигаем корутины до завершения
        advanceUntilIdle()

        // Проверяем, что вызван метод сохранения
        coVerify { usecases.upsertArticle(article) }
        // Проверяем, что sideEffect выставлен корректно
        assertThat(viewModel.sideEffect).isEqualTo("Article Saved")
    }

    @Test
    fun `calls delete when article exists`() = runTest {
        // Подготавливаем статью
        val article = Article(
            url = "url123",
            title = "Title",
            description = "Description",
            content = "Content",
            publishedAt = "2024-01-01",
            source = Source(id = "2", name = "abc"),
            author = "Author",
            urlToImage = ""
        )

        // Имитируем, что статья уже есть в базе
        coEvery { usecases.getArticle(article.url) } returns article

        // Инициализация ViewModel
        viewModel = DetailsViewModel(usecases)
        // Отправляем событие
        viewModel.onEvent(DetailsEvent.UpsertDeleteArticle(article))
        advanceUntilIdle()

        // Проверяем, что вызван метод удаления
        coVerify { usecases.deleteArticle(article) }
        // Проверяем, что sideEffect соответствует удалению
        assertThat(viewModel.sideEffect).isEqualTo("Article Deleted")
    }

    @Test
    fun `RemoveSideEffect resets sideEffect`() = runTest {
        // Инициализация ViewModel
        viewModel = DetailsViewModel(usecases)
        // Отправляем событие сброса sideEffect
        viewModel.onEvent(DetailsEvent.RemoveSideEffect)

        // Проверяем, что sideEffect стал null
        assertThat(viewModel.sideEffect).isNull()
    }
}
