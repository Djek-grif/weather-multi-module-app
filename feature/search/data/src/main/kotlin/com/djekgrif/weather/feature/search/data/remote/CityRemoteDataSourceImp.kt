package com.djekgrif.weather.feature.search.data.remote

import com.djekgrif.weather.core.data.network.getResult
import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.core.domain.util.map
import com.djekgrif.weather.feature.search.data.mapper.toCity
import com.djekgrif.weather.feature.search.data.remote.dto.CityDto
import com.djekgrif.weather.feature.search.domain.model.City
import io.ktor.client.HttpClient

class CityRemoteDataSourceImp(
    private val httpClient: HttpClient,
) : CityRemoteDataSource {

    companion object {
        private const val GEO_DIRECT_ROUTE = "/geo/1.0/direct"
        private const val RESULT_LIMIT = 8
    }

    override suspend fun searchCities(query: String): Result<List<City>, DataError.Network> =
        httpClient.getResult<List<CityDto>>(
            route = GEO_DIRECT_ROUTE,
            queryParameters = mapOf("q" to query, "limit" to RESULT_LIMIT),
        ).map { dtos -> dtos.map { it.toCity() } }
}