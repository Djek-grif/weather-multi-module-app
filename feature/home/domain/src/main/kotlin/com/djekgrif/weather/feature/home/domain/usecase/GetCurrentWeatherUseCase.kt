package com.djekgrif.weather.feature.home.domain.usecase

import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.home.domain.model.CurrentWeather
import com.djekgrif.weather.feature.home.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentWeatherUseCase(
    private val weatherRepository: WeatherRepository,
) {
    operator fun invoke(city: String): Flow<Result<CurrentWeather, DataError>> =
        weatherRepository.getCurrentWeather(city)
}