package com.djekgrif.weather.feature.home.domain.location

import com.djekgrif.weather.core.domain.util.Error

enum class LocationError : Error {
    LOCATION_UNAVAILABLE,
    CITY_NOT_FOUND,
    NETWORK,
}
