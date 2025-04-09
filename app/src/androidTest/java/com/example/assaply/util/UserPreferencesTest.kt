package com.example.assaply.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
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

@OptIn(ExperimentalCoroutinesApi::class)
class UserPreferencesTest {

    private lateinit var context: Context
    private lateinit var userPreferences: UserPreferences

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        context = ApplicationProvider.getApplicationContext()
        userPreferences = UserPreferences(context)
        runBlocking {
            context.dataStore.edit { it.clear() }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun readAppEntryReturnsFalseByDefault() = runTest {
        val result = userPreferences.readAppEntry().first()
        assertEquals(false, result)
    }

    @Test
    fun saveAppEntrySetsAppEntryToTrue() = runTest {
        userPreferences.saveAppEntry()
        val result = userPreferences.readAppEntry().first()
        assertEquals(true, result)
    }
}
