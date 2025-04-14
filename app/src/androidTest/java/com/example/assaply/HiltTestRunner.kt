package com.example.assaply

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Кастомный TestRunner, необходимый для интеграции с Hilt.
 * Заменяет стандартное Application на HiltTestApplication при запуске androidTest.
 *
 * Обязательно указывается в build.gradle:
 * defaultConfig.testInstrumentationRunner = "com.example.assaply.HiltTestRunner"
 */
class HiltTestRunner : AndroidJUnitRunner() {

    /**
     * Переопределяет создание Application для тестов.
     * Используется HiltTestApplication для поддержки внедрения зависимостей.
     */
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
