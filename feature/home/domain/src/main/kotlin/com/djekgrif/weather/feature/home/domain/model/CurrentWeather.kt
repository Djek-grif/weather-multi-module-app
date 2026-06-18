package com.djekgrif.weather.feature.home.domain.model

/** Current weather conditions for a city. Pure domain model — no formatting, no UI concerns. */
data class CurrentWeather(
    val cityName: String,
    val temperature: Double,
    val feelsLike: Double,
    val description: String,
    val iconCode: String,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Int,
    val sunrise: Long,
    val sunset: Long,
    /** The city's shift in seconds from UTC, used to render sunrise/sunset in local time. */
    val timezoneOffsetSeconds: Int,
)