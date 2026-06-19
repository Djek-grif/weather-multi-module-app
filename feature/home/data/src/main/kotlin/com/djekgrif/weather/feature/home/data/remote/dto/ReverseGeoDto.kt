package com.djekgrif.weather.feature.home.data.remote.dto

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ReverseGeoDto(
    val name: String = "",
    val country: String = "",
)