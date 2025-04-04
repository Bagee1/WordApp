package com.example.wordd.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREFERENCES_NAME = "word_settings"
private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

class DataStoreManager(private val context: Context) {

    companion object {
        val DISPLAY_MODE = intPreferencesKey("display_mode")  // 0: Бүгд, 1: Зөвхөн гадаад, 2: Зөвхөн монгол
    }

    val displayModeFlow: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[DISPLAY_MODE] ?: 0 }

    suspend fun setDisplayMode(mode: Int) {
        context.dataStore.edit { preferences ->
            preferences[DISPLAY_MODE] = mode
        }
    }
}
