package com.example.assaply.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    private val context: Context
){
    suspend fun saveAppEntry() {
        context.dataStore.edit{ settings ->
            settings[PreferencesKeys.APP_ENTRY] = true
        }
    }

    fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map{ preferences ->
            preferences[PreferencesKeys.APP_ENTRY] == true
        }
    }


}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.USER_SETTINGS)
private object PreferencesKeys{
    val APP_ENTRY = booleanPreferencesKey(name = Constants.APP_ENTRY)
}