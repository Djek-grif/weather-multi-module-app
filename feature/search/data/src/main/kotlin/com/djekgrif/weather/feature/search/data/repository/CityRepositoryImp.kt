package com.djekgrif.weather.feature.search.data.repository

import com.djekgrif.weather.core.data.preferences.PreferencesDataSource
import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.search.data.remote.CityRemoteDataSource
import com.djekgrif.weather.feature.search.domain.model.City
import com.djekgrif.weather.feature.search.domain.repository.CityRepository

class CityRepositoryImp(
    private val remoteDataSource: CityRemoteDataSource,
    private val preferencesDataSource: PreferencesDataSource,
) : CityRepository {

    override suspend fun searchCities(query: String): Result<List<City>, DataError> =
        remoteDataSource.searchCities(query)

    override suspend fun selectCity(cityName: String) {
        preferencesDataSource.putString(PreferencesDataSource.SELECTED_CITY_KEY, cityName)
    }
}