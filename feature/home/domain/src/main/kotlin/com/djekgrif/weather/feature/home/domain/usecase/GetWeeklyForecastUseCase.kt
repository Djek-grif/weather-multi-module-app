package com.djekgrif.weather.feature.home.domain.usecase

import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.home.domain.model.DailyForecast
import com.djekgrif.weather.feature.home.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetWeeklyForecastUseCase(
    private val weatherRepository: WeatherRepository,
) {
    operator fun invoke(city: String): Flow<Result<List<DailyForecast>, DataError>> =
        weatherRepository.getWeeklyForecast(city)
}