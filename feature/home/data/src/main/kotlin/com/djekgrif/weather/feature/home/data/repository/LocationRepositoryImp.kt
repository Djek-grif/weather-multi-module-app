package com.djekgrif.weather.feature.home.data.repository

import com.djekgrif.weather.core.domain.location.LocationProvider
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.home.data.remote.LocationRemoteDataSource
import com.djekgrif.weather.feature.home.domain.location.LocationError
import com.djekgrif.weather.feature.home.domain.repository.LocationRepository

class LocationRepositoryImp(
    private val locationProvider: LocationProvider,
    private val remoteDataSource: LocationRemoteDataSource,
) : LocationRepository {

    override suspend fun getCurrentCity(): Result<String, LocationError> {
        val coordinates = locationProvider.getCurrentCoordinates()
            ?: return Result.Error(LocationError.LOCATION_UNAVAILABLE)

        return when (
            val result = remoteDataSource.reverseGeocode(
                latitude = coordinates.latitude,
                longitude = coordinates.longitude,
            )
        ) {
            is Result.Success -> result.data
                ?.let { Result.Success(it) }
                ?: Result.Error(LocationError.CITY_NOT_FOUND)

            is Result.Error -> Result.Error(LocationError.NETWORK)
        }
    }
}