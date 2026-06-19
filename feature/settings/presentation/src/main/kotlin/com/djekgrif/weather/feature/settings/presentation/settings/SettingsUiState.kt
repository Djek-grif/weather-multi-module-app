package com.djekgrif.weather.feature.settings.presentation.settings

import androidx.compose.runtime.Immutable
import com.djekgrif.weather.core.domain.settings.TemperatureUnit

@Immutable
data class SettingsUiState(
    val selectedUnit: TemperatureUnit = TemperatureUnit.CELSIUS,
)
