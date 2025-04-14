package com.example.assaply.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.assaply.data.domain.entities.Article
import com.example.assaply.data.domain.entities.Source
import com.example.assaply.data.room.NewsDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assaply.data.room.NewsDatabase

/**
 * Интеграционный тест для [NewsDao].
 * Тесты используют in-memory базу данных для имитации реального взаимодействия с Room.
 */
@RunWith(AndroidJUnit4::class)
class NewsDaoTest {

    private lateinit var db: NewsDatabase
    private lateinit var dao: NewsDao

    // Пример статьи для использования в тестах
    private val article = Article(
        title = "Test Article",
        description = "Some description",
        url = "https://example.com",
        author = "Author",
        content = "Full content",
        publishedAt = "2025-01-01",
        source = Source("1", "source name"),
        urlToImage = "https://image.jpg"
    )

    /**
     * Инициализация in-memory базы данных и получение DAO.
     */
    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.newsDao
    }

    /**
     * Закрытие базы данных после каждого теста.
     */
    @After
    fun tearDown() {
        db.close()
    }

    /**
     * Проверяет, что статья может быть добавлена и получена по URL.
     */
    @Test
    fun insertAndGetArticle() = runTest {
        dao.upsert(article)
        val result = dao.getArticle(article.url)
        assertEquals(article.title, result?.title)
    }

    /**
     * Проверяет, что список всех статей возвращает вставленную статью.
     */
    @Test
    fun insertAndGetAllArticles() = runTest {
        dao.upsert(article)
        val result = dao.getArticles().first()
        assertEquals(1, result.size)
        assertEquals("Test Article", result[0].title)
    }

    /**
     * Проверяет удаление статьи из базы данных.
     */
    @Test
    fun deleteArticle() = runTest {
        dao.upsert(article)
        dao.delete(article)
        val result = dao.getArticles().first()
        assertEquals(0, result.size)
    }
}
