package com.djekgrif.weather.feature.home.domain

import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.home.domain.model.CurrentWeather
import com.djekgrif.weather.feature.home.domain.model.DailyForecast
import com.djekgrif.weather.feature.home.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

class FakeWeatherRepository : WeatherRepository {
    var currentWeatherResult: Result<CurrentWeather, DataError> =
        Result.Success(sampleCurrentWeather)
    var weeklyForecastResult: Result<List<DailyForecast>, DataError> =
        Result.Success(emptyList())

    private val selectedCity = MutableStateFlow<String?>(null)
    val savedCities = mutableListOf<String>()

    override fun getCurrentWeather(city: String): Flow<Result<CurrentWeather, DataError>> =
        flowOf(currentWeatherResult)

    override fun getWeeklyForecast(city: String): Flow<Result<List<DailyForecast>, DataError>> =
        flowOf(weeklyForecastResult)

    override fun getSelectedCity(): Flow<String?> = selectedCity.asStateFlow()

    override suspend fun saveSelectedCity(city: String) {
        savedCities += city
        selectedCity.value = city
    }

    companion object {
        val sampleCurrentWeather = CurrentWeather(
            cityName = "London",
            temperature = 20.0,
            feelsLike = 19.0,
            description = "clear sky",
            iconCode = "01d",
            humidity = 50,
            windSpeed = 3.0,
            pressure = 1012,
            sunrise = 1_700_000_000L,
            sunset = 1_700_040_000L,
        )
    }
}
