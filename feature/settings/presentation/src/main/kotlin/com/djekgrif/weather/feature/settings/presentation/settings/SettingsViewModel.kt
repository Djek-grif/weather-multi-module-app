package com.djekgrif.weather.feature.settings.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djekgrif.weather.core.domain.settings.GetTemperatureUnitUseCase
import com.djekgrif.weather.core.domain.settings.SetTemperatureUnitUseCase
import com.djekgrif.weather.core.domain.settings.TemperatureUnit
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    getTemperatureUnit: GetTemperatureUnitUseCase,
    private val setTemperatureUnit: SetTemperatureUnitUseCase,
) : ViewModel() {

    val state: StateFlow<SettingsUiState> = getTemperatureUnit()
        .map { SettingsUiState(selectedUnit = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingsUiState(),
        )

    fun onUnitSelected(unit: TemperatureUnit) {
        viewModelScope.launch { setTemperatureUnit(unit) }
    }
}
