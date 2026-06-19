package com.djekgrif.weather.feature.search.data.remote.dto

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class CityDto(
    val name: String = "",
    @SerialName("lat") val latitude: Double = 0.0,
    @SerialName("lon") val longitude: Double = 0.0,
    val country: String = "",
    val state: String? = null,
)