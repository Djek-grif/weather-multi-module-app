package com.djekgrif.weather.feature.search.domain.usecase

import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.search.domain.model.City
import com.djekgrif.weather.feature.search.domain.repository.CityRepository

class SearchCitiesUseCase(
    private val cityRepository: CityRepository,
) {
    suspend operator fun invoke(query: String): Result<List<City>, DataError> =
        cityRepository.searchCities(query)
}