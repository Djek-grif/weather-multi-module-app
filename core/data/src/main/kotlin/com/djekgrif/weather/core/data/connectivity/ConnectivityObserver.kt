package com.djekgrif.weather.core.data.connectivity

import kotlinx.coroutines.flow.Flow

/** Emits the device's network availability so the UI can react to going offline/online. */
interface ConnectivityObserver {
    val isConnected: Flow<Boolean>
}