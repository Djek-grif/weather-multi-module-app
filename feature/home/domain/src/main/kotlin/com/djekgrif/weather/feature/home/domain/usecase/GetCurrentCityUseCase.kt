package com.djekgrif.weather.feature.home.domain.usecase

import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.home.domain.location.LocationError
import com.djekgrif.weather.feature.home.domain.repository.LocationRepository

class GetCurrentCityUseCase(
    private val locationRepository: LocationRepository,
) {
    suspend operator fun invoke(): Result<String, LocationError> =
        locationRepository.getCurrentCity()
}