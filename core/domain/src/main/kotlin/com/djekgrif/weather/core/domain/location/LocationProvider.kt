package com.djekgrif.weather.core.domain.location

/** Abstraction over the device's location services. */
interface LocationProvider {
    /** The device's current coordinates, or null if unavailable (no permission, GPS off, etc.). */
    suspend fun getCurrentCoordinates(): Coordinates?
}