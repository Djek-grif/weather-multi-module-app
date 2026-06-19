package com.djekgrif.weather.core.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import com.djekgrif.weather.core.domain.location.Coordinates
import com.djekgrif.weather.core.domain.location.LocationProvider
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await

class LocationProviderImp(
    private val context: Context,
) : LocationProvider {

    private val client = LocationServices.getFusedLocationProviderClient(context)

    override suspend fun getCurrentCoordinates(): Coordinates? {
        if (!hasLocationPermission()) return null
        return try {
            val location = client.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                null,
            ).await() ?: client.lastLocation.await()
            location?.let { Coordinates(it.latitude, it.longitude) }
        } catch (e: SecurityException) {
            e.printStackTrace()
            null
        }
    }

    private fun hasLocationPermission(): Boolean {
        val fine = context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        val coarse = context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        return fine == PackageManager.PERMISSION_GRANTED ||
            coarse == PackageManager.PERMISSION_GRANTED
    }
}