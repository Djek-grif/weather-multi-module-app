package com.djekgrif.weather.feature.search.domain.repository

import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.search.domain.model.City

interface CityRepository {
    suspend fun searchCities(query: String): Result<List<City>, DataError>

    /** Persists the user's chosen city so the home feature observes it. */
    suspend fun selectCity(cityName: String)
}