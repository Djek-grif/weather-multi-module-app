package com.djekgrif.weather.core.data.preferences

import kotlinx.coroutines.flow.Flow

/**
 * General app-wide key-value preferences abstraction (DataStore-backed). Features store their
 * own values under their own keys — there is no per-feature preferences source.
 */
interface PreferencesDataSource {

    companion object {
        const val SELECTED_CITY_KEY = "selected_city"
    }
    fun getString(key: String, defaultValue: String? = null): Flow<String?>
    suspend fun putString(key: String, value: String)

    fun getInt(key: String, defaultValue: Int): Flow<Int>
    suspend fun putInt(key: String, value: Int)

    fun getLong(key: String, defaultValue: Long): Flow<Long>
    suspend fun putLong(key: String, value: Long)

    fun getBoolean(key: String, defaultValue: Boolean): Flow<Boolean>
    suspend fun putBoolean(key: String, value: Boolean)

    fun getDouble(key: String, defaultValue: Double): Flow<Double>
    suspend fun putDouble(key: String, value: Double)

    suspend fun remove(key: String)
    suspend fun clear()
}