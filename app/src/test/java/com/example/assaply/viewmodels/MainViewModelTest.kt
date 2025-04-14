package com.example.assaply.viewmodels

import com.example.assaply.MainViewModel
import com.example.assaply.presentation.navigation.components.Route
import com.example.assaply.util.UserPreferences
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MainViewModel
    private lateinit var userPreferences: UserPreferences

    @Before
    fun setup() {
        // Устанавливаем Test Dispatcher в качестве главного диспетчера
        Dispatchers.setMain(testDispatcher)
        // Инициализируем mock объекты
        MockKAnnotations.init(this)
        // Создаём мок userPreferences
        userPreferences = mockk()
    }

    @After
    fun tearDown() {
        // Сбрасываем главный диспетчер после каждого теста
        Dispatchers.resetMain()
    }

    @Test
    fun `startDestination is NEWS_NAV if user has entered app before`() = runTest {
        // Подготовка: userPreferences возвращает true, как будто пользователь уже заходил
        every { userPreferences.readAppEntry() } returns flowOf(true)

        // Инициализируем ViewModel
        viewModel = MainViewModel(userPreferences)

        // Продвигаем время, чтобы viewModel успела обработать flow
        advanceTimeBy(300)
        advanceUntilIdle()

        // Проверка: стартовый экран — экран новостей
        assertThat(viewModel.startDestination).isEqualTo(Route.NEWS_NAV)
        // Проверка: splash экран завершён
        assertThat(viewModel.splashCondition).isFalse()
    }

    @Test
    fun `startDestination is APP_START_NAV if user is new`() = runTest {
        // Подготовка: userPreferences возвращает false, как будто пользователь первый раз заходит
        every { userPreferences.readAppEntry() } returns flowOf(false)

        // Инициализируем ViewModel
        viewModel = MainViewModel(userPreferences)

        // Продвигаем время
        advanceTimeBy(300)
        advanceUntilIdle()

        // Проверка: стартовый экран — экран начала
        assertThat(viewModel.startDestination).isEqualTo(Route.APP_START_NAV)
        // Проверка: splash экран завершён
        assertThat(viewModel.splashCondition).isFalse()
    }
}
