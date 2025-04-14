package com.example.assaply.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Интеграционный тест для класса [UserPreferences], работающего с DataStore.
 * Тестирует корректность сохранения и чтения значения признака первого входа в приложение.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class UserPreferencesTest {

    private lateinit var context: Context
    private lateinit var userPreferences: UserPreferences

    /**
     * Установка контекста приложения и очистка DataStore перед каждым тестом.
     */
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        context = ApplicationProvider.getApplicationContext()
        userPreferences = UserPreferences(context)
        runBlocking {
            context.dataStore.edit { it.clear() }
        }
    }

    /**
     * Сброс диспетчера корутин после завершения тестов.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Проверяет, что по умолчанию значение appEntry — false.
     */
    @Test
    fun readAppEntryReturnsFalseByDefault() = runTest {
        val result = userPreferences.readAppEntry().first()
        assertEquals(false, result)
    }

    /**
     * Проверяет, что после вызова saveAppEntry() значение становится true.
     */
    @Test
    fun saveAppEntrySetsAppEntryToTrue() = runTest {
        userPreferences.saveAppEntry()
        val result = userPreferences.readAppEntry().first()
        assertEquals(true, result)
    }
}
