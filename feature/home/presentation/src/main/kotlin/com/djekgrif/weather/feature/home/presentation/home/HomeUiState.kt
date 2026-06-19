package com.djekgrif.weather.feature.home.presentation.home

import androidx.compose.runtime.Immutable
import com.djekgrif.weather.core.presentation.ui.UiText
import com.djekgrif.weather.feature.home.presentation.model.CurrentWeatherUi
import com.djekgrif.weather.feature.home.presentation.model.DailyForecastUi
import com.djekgrif.weather.feature.home.presentation.model.HomeTab

@Immutable
data class HomeUiState(
    val cityName: String = "",
    val selectedTab: HomeTab = HomeTab.Today,
    val currentWeather: CurrentWeatherUi? = null,
    val forecast: List<DailyForecastUi> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: UiText? = null,
    val showLocationPrompt: Boolean = false,
    val isDetectingLocation: Boolean = false,
    val locationError: UiText? = null,
    val isOffline: Boolean = false,
)