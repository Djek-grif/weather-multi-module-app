package com.djekgrif.weather.core.domain.settings

import kotlinx.coroutines.flow.Flow

class GetTemperatureUnitUseCase(
    private val settingsRepository: SettingsRepository,
) {
    operator fun invoke(): Flow<TemperatureUnit> = settingsRepository.getTemperatureUnit()
}
