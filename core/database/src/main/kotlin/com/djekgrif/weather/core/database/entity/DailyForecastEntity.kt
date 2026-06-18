package com.djekgrif.weather.core.database.entity

import androidx.room.Entity

@Entity(tableName = "daily_forecast", primaryKeys = ["cityName", "date"])
data class DailyForecastEntity(
    val cityName: String,
    val date: Long,
    val tempMin: Double,
    val tempMax: Double,
    val description: String,
    val iconCode: String,
    val humidity: Int,
    val windSpeed: Double,
    /** Epoch millis the rows for this city were cached, for TTL checks. */
    val cachedAt: Long,
)