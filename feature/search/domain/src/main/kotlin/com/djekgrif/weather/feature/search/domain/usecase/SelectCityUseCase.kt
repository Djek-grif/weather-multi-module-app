package com.djekgrif.weather.feature.search.domain.usecase

import com.djekgrif.weather.feature.search.domain.repository.CityRepository

class SelectCityUseCase(
    private val cityRepository: CityRepository,
) {
    suspend operator fun invoke(cityName: String) = cityRepository.selectCity(cityName)
}