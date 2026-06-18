package com.djekgrif.weather.core.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferencesDataSource(
    private val dataStore: DataStore<Preferences>,
) : PreferencesDataSource {

    override fun getString(key: String, defaultValue: String?): Flow<String?> =
        dataStore.data.map { it[stringPreferencesKey(key)] ?: defaultValue }

    override suspend fun putString(key: String, value: String) {
        dataStore.edit { it[stringPreferencesKey(key)] = value }
    }

    override fun getInt(key: String, defaultValue: Int): Flow<Int> =
        dataStore.data.map { it[intPreferencesKey(key)] ?: defaultValue }

    override suspend fun putInt(key: String, value: Int) {
        dataStore.edit { it[intPreferencesKey(key)] = value }
    }

    override fun getLong(key: String, defaultValue: Long): Flow<Long> =
        dataStore.data.map { it[longPreferencesKey(key)] ?: defaultValue }

    override suspend fun putLong(key: String, value: Long) {
        dataStore.edit { it[longPreferencesKey(key)] = value }
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Flow<Boolean> =
        dataStore.data.map { it[booleanPreferencesKey(key)] ?: defaultValue }

    override suspend fun putBoolean(key: String, value: Boolean) {
        dataStore.edit { it[booleanPreferencesKey(key)] = value }
    }

    override fun getDouble(key: String, defaultValue: Double): Flow<Double> =
        dataStore.data.map { it[doublePreferencesKey(key)] ?: defaultValue }

    override suspend fun putDouble(key: String, value: Double) {
        dataStore.edit { it[doublePreferencesKey(key)] = value }
    }

    override suspend fun remove(key: String) {
        dataStore.edit { preferences ->
            preferences.asMap().keys
                .filter { it.name == key }
                .forEach { preferences.remove(it) }
        }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}