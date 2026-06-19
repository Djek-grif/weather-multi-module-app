package com.djekgrif.weather.feature.search.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class CityUi(
    val name: String,
    val region: String,
)