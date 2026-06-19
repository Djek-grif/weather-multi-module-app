package com.djekgrif.weather.feature.search.domain.model

/** A city suggestion from geocoding. */
data class City(
    val name: String,
    val country: String,
    val state: String?,
    val latitude: Double,
    val longitude: Double,
)