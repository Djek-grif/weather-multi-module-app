package com.djekgrif.weather.core.data.settings

import com.djekgrif.weather.core.data.preferences.PreferencesDataSource
import com.djekgrif.weather.core.domain.settings.SettingsRepository
import com.djekgrif.weather.core.domain.settings.TemperatureUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImp(
    private val preferencesDataSource: PreferencesDataSource,
) : SettingsRepository {

    override fun getTemperatureUnit(): Flow<TemperatureUnit> =
        preferencesDataSource.getString(PreferencesDataSource.TEMPERATURE_UNIT_KEY)
            .map { stored ->
                stored?.let { runCatching { TemperatureUnit.valueOf(it) }.getOrNull() }
                    ?: TemperatureUnit.CELSIUS
            }

    override suspend fun setTemperatureUnit(unit: TemperatureUnit) {
        preferencesDataSource.putString(PreferencesDataSource.TEMPERATURE_UNIT_KEY, unit.name)
    }
}
