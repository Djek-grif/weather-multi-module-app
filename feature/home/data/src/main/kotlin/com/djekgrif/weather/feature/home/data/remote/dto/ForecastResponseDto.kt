package com.djekgrif.weather.feature.home.data.remote.dto

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ForecastResponseDto(
    val list: List<ForecastItemDto> = emptyList(),
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ForecastItemDto(
    val dt: Long = 0,
    val main: MainDto = MainDto(),
    @SerialName("weather") val weather: List<WeatherDescriptionDto> = emptyList(),
    @SerialName("wind") val wind: WindDto = WindDto(),
    @SerialName("dt_txt") val dateText: String = "",
)