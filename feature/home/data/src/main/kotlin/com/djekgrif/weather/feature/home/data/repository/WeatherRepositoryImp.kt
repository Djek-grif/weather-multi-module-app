package com.djekgrif.weather.feature.home.data.repository

import com.djekgrif.weather.core.data.preferences.PreferencesDataSource
import com.djekgrif.weather.core.domain.dispatcher.DispatcherProvider
import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.home.data.local.WeatherLocalDataSource
import com.djekgrif.weather.feature.home.data.remote.WeatherRemoteDataSource
import com.djekgrif.weather.feature.home.domain.model.CurrentWeather
import com.djekgrif.weather.feature.home.domain.model.DailyForecast
import com.djekgrif.weather.feature.home.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Offline-first: emits cached data first (if any), then fetches fresh data, persists it, and
 * emits it. On network failure it keeps the cached value, or surfaces the error if nothing
 * was cached. The selected city is delegated to the shared [PreferencesDataSource].
 */
class WeatherRepositoryImp(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource,
    private val preferencesDataSource: PreferencesDataSource,
    private val dispatchers: DispatcherProvider,
) : WeatherRepository {

    override fun getCurrentWeather(city: String): Flow<Result<CurrentWeather, DataError>> = flow {
        val cached = localDataSource.getCurrentWeather(city)
        if (cached != null) emit(Result.Success(cached))

        when (val remote = remoteDataSource.getCurrentWeather(city)) {
            is Result.Success -> {
                localDataSource.upsertCurrentWeather(remote.data, System.currentTimeMillis())
                emit(Result.Success(remote.data))
            }

            is Result.Error -> {
                if (cached == null) emit(Result.Error(remote.error))
            }
        }
    }.flowOn(dispatchers.io)

    override fun getWeeklyForecast(
        city: String,
    ): Flow<Result<List<DailyForecast>, DataError>> = flow {
        val cached = localDataSource.getForecast(city)
        if (cached.isNotEmpty()) emit(Result.Success(cached))

        when (val remote = remoteDataSource.getWeeklyForecast(city)) {
            is Result.Success -> {
                localDataSource.replaceForecast(city, remote.data, System.currentTimeMillis())
                emit(Result.Success(remote.data))
            }

            is Result.Error -> {
                if (cached.isEmpty()) emit(Result.Error(remote.error))
            }
        }
    }.flowOn(dispatchers.io)

    override fun getSelectedCity(): Flow<String?> =
        preferencesDataSource.getString(PreferencesDataSource.SELECTED_CITY_KEY)

    override suspend fun saveSelectedCity(city: String) {
        preferencesDataSource.putString(PreferencesDataSource.SELECTED_CITY_KEY, city)
    }
}