package com.djekgrif.weather.feature.home.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class CurrentWeatherUi(
    val cityName: String,
    val temperature: String,
    val highTemperature: String,
    val lowTemperature: String,
    val feelsLike: String,
    val description: String,
    val iconUrl: String,
    val humidity: String,
    val windSpeed: String,
    val pressure: String,
    val sunrise: String,
    val sunset: String,
    /** Sun position along the sunrise→sunset arc, 0f..1f. */
    val sunProgress: Float,
    /** Relative "Updated 5 min ago" label; null when the refresh time is unknown. */
    val lastUpdatedLabel: String? = null,
)

@Immutable
data class DailyForecastUi(
    val id: Long,
    val dayName: String,
    val iconUrl: String,
    val description: String,
    val high: String,
    val low: String,
    val isToday: Boolean,
)

enum class HomeTab { Today, Week }