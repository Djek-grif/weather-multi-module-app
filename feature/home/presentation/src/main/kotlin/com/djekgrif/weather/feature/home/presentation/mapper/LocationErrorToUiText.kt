package com.djekgrif.weather.feature.home.presentation.mapper

import com.djekgrif.weather.core.presentation.ui.UiText
import com.djekgrif.weather.feature.home.domain.location.LocationError
import com.djekgrif.weather.feature.home.presentation.R

fun LocationError.toUiText(): UiText = when (this) {
    LocationError.LOCATION_UNAVAILABLE ->
        UiText.StringResource(R.string.error_location_unavailable)

    LocationError.CITY_NOT_FOUND ->
        UiText.StringResource(R.string.error_location_city_not_found)

    LocationError.NETWORK ->
        UiText.StringResource(R.string.error_location_network)
}