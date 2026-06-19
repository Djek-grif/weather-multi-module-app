package com.djekgrif.weather.feature.search.data.remote

import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.search.domain.model.City

interface CityRemoteDataSource {
    suspend fun searchCities(query: String): Result<List<City>, DataError.Network>
}