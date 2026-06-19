package com.djekgrif.weather.core.domain.settings

class SetTemperatureUnitUseCase(
    private val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke(unit: TemperatureUnit) =
        settingsRepository.setTemperatureUnit(unit)
}
