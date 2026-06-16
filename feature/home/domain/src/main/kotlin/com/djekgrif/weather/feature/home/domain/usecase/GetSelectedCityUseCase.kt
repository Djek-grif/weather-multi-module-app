package com.djekgrif.weather.feature.home.domain.usecase

import com.djekgrif.weather.feature.home.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetSelectedCityUseCase(
    private val weatherRepository: WeatherRepository,
) {
    operator fun invoke(): Flow<String?> = weatherRepository.getSelectedCity()
}