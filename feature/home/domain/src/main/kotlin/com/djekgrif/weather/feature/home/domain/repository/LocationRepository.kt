package com.djekgrif.weather.feature.home.domain.repository

import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.home.domain.location.LocationError

interface LocationRepository {
    /** Resolves the device's current location to a city name. */
    suspend fun getCurrentCity(): Result<String, LocationError>
}