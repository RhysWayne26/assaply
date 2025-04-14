package com.example.assaply.api

import com.example.assaply.data.api.NewsApi
import com.example.assaply.data.api.NewsResponse
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Интеграционный тест для [NewsApi] с использованием [MockWebServer].
 * Проверяет корректность обработки успешного ответа от API.
 */
class NewsApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: NewsApi

    /**
     * Настройка тестового окружения:
     * - Запуск MockWebServer.
     * - Инициализация Retrofit с базовым URL от MockWebServer.
     * - Создание экземпляра NewsApi.
     */
    @Before
    fun setup() {
        server = MockWebServer()
        server.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(NewsApi::class.java)
    }

    /**
     * Очистка ресурсов после тестов:
     * - Остановка MockWebServer.
     */
    @After
    fun teardown() {
        server.shutdown()
    }

    /**
     * Тестирует метод getNews:
     * - Эмулирует успешный HTTP-ответ с одним новостным элементом.
     * - Проверяет, что данные корректно десериализуются и соответствуют ожиданиям.
     */
    @Test
    fun `getNews returns expected article list`() = runTest {
        val mockJson = """
        {
          "status": "ok",
          "totalResults": 1,
          "articles": [
            {
              "source": { "id": "abc-news", "name": "ABC News" },
              "author": "John Doe",
              "title": "Test Title",
              "description": "Description",
              "url": "https://example.com/article",
              "urlToImage": "https://example.com/image.jpg",
              "publishedAt": "2025-01-01T00:00:00Z",
              "content": "Full content"
            }
          ]
        }
        """.trimIndent()

        // Добавляем в очередь MockWebServer подготовленный ответ
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockJson)
        )

        // Выполняем запрос к API
        val result: NewsResponse = api.getNews(
            page = 1,
            sources = "abc-news",
            query = "test"
        )

        // Проверяем корректность полученных данных
        assertEquals("ok", result.status)
        assertEquals(1, result.articles.size)
        assertEquals("Test Title", result.articles[0].title)
        assertEquals("John Doe", result.articles[0].author)
    }
}
