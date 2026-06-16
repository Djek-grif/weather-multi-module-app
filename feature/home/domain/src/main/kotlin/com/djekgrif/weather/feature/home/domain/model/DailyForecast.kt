package com.djekgrif.weather.feature.home.domain.model

/** Aggregated forecast for a single day. */
data class DailyForecast(
    val date: Long,
    val tempMin: Double,
    val tempMax: Double,
    val description: String,
    val iconCode: String,
    val humidity: Int,
    val windSpeed: Double,
)