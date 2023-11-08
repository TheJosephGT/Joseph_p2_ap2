package com.example.joseph_p2_ap2.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val PREFERENCE_NAME="CounterStore"
private const val COUNTER_KEY="CounterKey"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

class CounterRepository @Inject constructor(
    private val context: Context
){
    private object PreferencesKeys {
        val COUNTER = intPreferencesKey(COUNTER_KEY)
    }

    val counter: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.COUNTER] ?: 0
    }

    suspend fun increment() {
        context.dataStore.edit { preferences ->
            val currentCounterValue = preferences[PreferencesKeys.COUNTER] ?: 0
            preferences[PreferencesKeys.COUNTER] = currentCounterValue + 1
        }
    }
}