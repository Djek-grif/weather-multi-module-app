package com.djekgrif.weather.feature.home.data.remote.dto

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class WeatherResponseDto(
    val weather: List<WeatherDescriptionDto> = emptyList(),
    val main: MainDto,
    val wind: WindDto = WindDto(),
    val sys: SysDto = SysDto(),
    val name: String = "",
    val dt: Long = 0,
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class WeatherDescriptionDto(
    val description: String = "",
    val icon: String = "",
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class MainDto(
    val temp: Double = 0.0,
    @SerialName("feels_like") val feelsLike: Double = 0.0,
    @SerialName("temp_min") val tempMin: Double = 0.0,
    @SerialName("temp_max") val tempMax: Double = 0.0,
    @SerialName("pressure") val pressure: Int = 0,
    @SerialName("humidity") val humidity: Int = 0,
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class WindDto(
    val speed: Double = 0.0,
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class SysDto(
    val sunrise: Long = 0,
    val sunset: Long = 0,
)