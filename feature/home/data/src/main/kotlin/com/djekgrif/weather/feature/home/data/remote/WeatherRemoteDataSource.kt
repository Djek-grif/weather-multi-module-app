package com.djekgrif.weather.feature.home.data.remote

import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.home.domain.model.CurrentWeather
import com.djekgrif.weather.feature.home.domain.model.DailyForecast

interface WeatherRemoteDataSource {
    suspend fun getCurrentWeather(city: String): Result<CurrentWeather, DataError.Network>
    suspend fun getWeeklyForecast(city: String): Result<List<DailyForecast>, DataError.Network>
}