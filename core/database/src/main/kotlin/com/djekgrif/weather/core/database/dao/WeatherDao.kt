package com.djekgrif.weather.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.djekgrif.weather.core.database.entity.CurrentWeatherEntity
import com.djekgrif.weather.core.database.entity.DailyForecastEntity

@Dao
interface WeatherDao {

    @Upsert
    suspend fun upsertCurrentWeather(weather: CurrentWeatherEntity)

    @Query("SELECT * FROM current_weather WHERE cityName = :city")
    suspend fun getCurrentWeather(city: String): CurrentWeatherEntity?

    @Query("SELECT * FROM daily_forecast WHERE cityName = :city ORDER BY date ASC")
    suspend fun getForecast(city: String): List<DailyForecastEntity>

    @Upsert
    suspend fun upsertForecast(forecast: List<DailyForecastEntity>)

    @Query("DELETE FROM daily_forecast WHERE cityName = :city")
    suspend fun clearForecast(city: String)

    /** Replaces the cached forecast for a city atomically. */
    @Transaction
    suspend fun replaceForecast(city: String, forecast: List<DailyForecastEntity>) {
        clearForecast(city)
        upsertForecast(forecast)
    }
}