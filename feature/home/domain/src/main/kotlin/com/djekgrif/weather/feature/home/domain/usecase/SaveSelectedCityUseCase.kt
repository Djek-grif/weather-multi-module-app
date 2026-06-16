package com.djekgrif.weather.feature.home.domain.usecase

import com.djekgrif.weather.feature.home.domain.repository.WeatherRepository

class SaveSelectedCityUseCase(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(city: String) = weatherRepository.saveSelectedCity(city)
}