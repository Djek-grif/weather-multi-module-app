package com.djekgrif.weather.feature.home.data.remote

import com.djekgrif.weather.core.data.network.getResult
import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.core.domain.util.map
import com.djekgrif.weather.feature.home.data.mapper.toCurrentWeather
import com.djekgrif.weather.feature.home.data.mapper.toDailyForecasts
import com.djekgrif.weather.feature.home.data.remote.dto.ForecastResponseDto
import com.djekgrif.weather.feature.home.data.remote.dto.WeatherResponseDto
import com.djekgrif.weather.feature.home.domain.model.CurrentWeather
import com.djekgrif.weather.feature.home.domain.model.DailyForecast
import io.ktor.client.HttpClient

class WeatherRemoteDataSourceImp(
    private val httpClient: HttpClient,
) : WeatherRemoteDataSource {

    companion object {
        private const val API_WEATHER_ROUTE = "/data/2.5/weather"
        private const val API_FORECAST_ROUTE = "/data/2.5/forecast"
    }

    override suspend fun getCurrentWeather(
        city: String,
    ): Result<CurrentWeather, DataError.Network> =
        httpClient.getResult<WeatherResponseDto>(
            route = API_WEATHER_ROUTE,
            queryParameters = mapOf("q" to city),
        ).map { it.toCurrentWeather() }

    override suspend fun getWeeklyForecast(
        city: String,
    ): Result<List<DailyForecast>, DataError.Network> =
        httpClient.getResult<ForecastResponseDto>(
            route = API_FORECAST_ROUTE,
            queryParameters = mapOf("q" to city),
        ).map { it.toDailyForecasts() }
}