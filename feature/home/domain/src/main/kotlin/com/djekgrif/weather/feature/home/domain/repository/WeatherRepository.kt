package com.djekgrif.weather.feature.home.domain.repository

import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.home.domain.model.CurrentWeather
import com.djekgrif.weather.feature.home.domain.model.DailyForecast
import kotlinx.coroutines.flow.Flow

/**
 * Coordinates remote + local weather sources. Weather reads are streams (offline-first:
 * emit cached data, then refreshed data), so the UI can observe updates reactively.
 */
interface WeatherRepository {
    fun getCurrentWeather(city: String): Flow<Result<CurrentWeather, DataError>>

    fun getWeeklyForecast(city: String): Flow<Result<List<DailyForecast>, DataError>>

    /** The user's currently selected city, or null if none has been chosen yet. */
    fun getSelectedCity(): Flow<String?>

    suspend fun saveSelectedCity(city: String)
}