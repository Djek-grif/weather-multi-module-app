package com.djekgrif.weather.feature.home.data.remote

import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result

interface LocationRemoteDataSource {
    /** Reverse-geocodes coordinates to a city name (null if no match). */
    suspend fun reverseGeocode(
        latitude: Double,
        longitude: Double,
    ): Result<String?, DataError.Network>
}