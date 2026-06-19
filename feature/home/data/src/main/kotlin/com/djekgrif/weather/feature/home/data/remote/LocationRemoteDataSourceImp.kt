package com.djekgrif.weather.feature.home.data.remote

import com.djekgrif.weather.core.data.network.getResult
import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.core.domain.util.map
import com.djekgrif.weather.feature.home.data.remote.dto.ReverseGeoDto
import io.ktor.client.HttpClient

class LocationRemoteDataSourceImp(
    private val httpClient: HttpClient,
) : LocationRemoteDataSource {

    companion object {
        private const val GEO_REVERSE_ROUTE = "/geo/1.0/reverse"
        private const val RESULT_LIMIT = 1
    }

    override suspend fun reverseGeocode(
        latitude: Double,
        longitude: Double,
    ): Result<String?, DataError.Network> =
        httpClient.getResult<List<ReverseGeoDto>>(
            route = GEO_REVERSE_ROUTE,
            queryParameters = mapOf(
                "lat" to latitude,
                "lon" to longitude,
                "limit" to RESULT_LIMIT,
            ),
        ).map { results -> results.firstOrNull()?.name?.takeIf { it.isNotBlank() } }
}