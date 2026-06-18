package com.djekgrif.weather.feature.home.data.repository

import com.djekgrif.weather.core.data.preferences.PreferencesDataSource
import com.djekgrif.weather.core.domain.dispatcher.DispatcherProvider
import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.home.data.local.WeatherLocalDataSource
import com.djekgrif.weather.feature.home.data.remote.WeatherRemoteDataSource
import com.djekgrif.weather.feature.home.domain.model.CurrentWeather
import com.djekgrif.weather.feature.home.domain.model.DailyForecast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FakeWeatherRemoteDataSource : WeatherRemoteDataSource {
    var currentWeatherResult: Result<CurrentWeather, DataError.Network> =
        Result.Error(DataError.Network.UNKNOWN)
    var weeklyForecastResult: Result<List<DailyForecast>, DataError.Network> =
        Result.Success(emptyList())

    override suspend fun getCurrentWeather(city: String) = currentWeatherResult
    override suspend fun getWeeklyForecast(city: String) = weeklyForecastResult
}

class FakeWeatherLocalDataSource : WeatherLocalDataSource {
    private val current = mutableMapOf<String, CurrentWeather>()
    private val forecast = mutableMapOf<String, List<DailyForecast>>()

    override suspend fun getCurrentWeather(city: String): CurrentWeather? = current[city]

    override suspend fun upsertCurrentWeather(weather: CurrentWeather, cachedAt: Long) {
        current[weather.cityName] = weather
    }

    override suspend fun getForecast(city: String): List<DailyForecast> =
        forecast[city] ?: emptyList()

    override suspend fun replaceForecast(
        city: String,
        forecast: List<DailyForecast>,
        cachedAt: Long,
    ) {
        this.forecast[city] = forecast
    }
}

class FakePreferencesDataSource : PreferencesDataSource {
    private val strings = mutableMapOf<String, MutableStateFlow<String?>>()
    private fun flowFor(key: String) = strings.getOrPut(key) { MutableStateFlow(null) }

    override fun getString(key: String, defaultValue: String?): Flow<String?> =
        flowFor(key).map { it ?: defaultValue }

    override suspend fun putString(key: String, value: String) {
        flowFor(key).value = value
    }

    override fun getInt(key: String, defaultValue: Int) = flowOf(defaultValue)
    override suspend fun putInt(key: String, value: Int) = Unit
    override fun getLong(key: String, defaultValue: Long) = flowOf(defaultValue)
    override suspend fun putLong(key: String, value: Long) = Unit
    override fun getBoolean(key: String, defaultValue: Boolean) = flowOf(defaultValue)
    override suspend fun putBoolean(key: String, value: Boolean) = Unit
    override fun getDouble(key: String, defaultValue: Double) = flowOf(defaultValue)
    override suspend fun putDouble(key: String, value: Double) = Unit
    override suspend fun remove(key: String) { strings.remove(key) }
    override suspend fun clear() { strings.clear() }
}

class TestDispatcherProvider(private val dispatcher: CoroutineDispatcher) : DispatcherProvider {
    override val main = dispatcher
    override val io = dispatcher
    override val default = dispatcher
}