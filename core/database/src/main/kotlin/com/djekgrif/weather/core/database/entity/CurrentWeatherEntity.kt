package com.djekgrif.weather.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeatherEntity(
    @PrimaryKey val cityName: String,
    val temperature: Double,
    val feelsLike: Double,
    val description: String,
    val iconCode: String,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Int,
    val sunrise: Long,
    val sunset: Long,
    /** Epoch millis the row was cached, for TTL checks. */
    val cachedAt: Long,
)