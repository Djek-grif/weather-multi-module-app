package com.djekgrif.weather.core.domain.settings

import kotlinx.coroutines.flow.Flow

/** App-wide user settings, consumed by multiple features (so it lives in :core, not a feature). */
interface SettingsRepository {
    fun getTemperatureUnit(): Flow<TemperatureUnit>
    suspend fun setTemperatureUnit(unit: TemperatureUnit)
}
