package com.djekgrif.weather.feature.home.data.local

import com.djekgrif.weather.feature.home.domain.model.CurrentWeather
import com.djekgrif.weather.feature.home.domain.model.DailyForecast

interface WeatherLocalDataSource {
    suspend fun getCurrentWeather(city: String): CurrentWeather?
    suspend fun upsertCurrentWeather(weather: CurrentWeather, cachedAt: Long)
    suspend fun getForecast(city: String): List<DailyForecast>
    suspend fun replaceForecast(city: String, forecast: List<DailyForecast>, cachedAt: Long)
}