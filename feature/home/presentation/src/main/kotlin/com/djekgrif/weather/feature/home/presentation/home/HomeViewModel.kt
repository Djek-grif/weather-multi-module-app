package com.djekgrif.weather.feature.home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djekgrif.weather.core.data.connectivity.ConnectivityObserver
import com.djekgrif.weather.core.domain.settings.GetTemperatureUnitUseCase
import com.djekgrif.weather.core.domain.settings.TemperatureUnit
import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.core.presentation.mapper.toUiText
import com.djekgrif.weather.core.presentation.ui.UiText
import com.djekgrif.weather.feature.home.domain.model.CurrentWeather
import com.djekgrif.weather.feature.home.domain.model.DailyForecast
import com.djekgrif.weather.feature.home.domain.usecase.GetCurrentCityUseCase
import com.djekgrif.weather.feature.home.domain.usecase.GetCurrentWeatherUseCase
import com.djekgrif.weather.feature.home.domain.usecase.GetSelectedCityUseCase
import com.djekgrif.weather.feature.home.domain.usecase.GetWeeklyForecastUseCase
import com.djekgrif.weather.feature.home.domain.usecase.SaveSelectedCityUseCase
import com.djekgrif.weather.feature.home.presentation.R
import com.djekgrif.weather.feature.home.presentation.mapper.toUi
import com.djekgrif.weather.feature.home.presentation.mapper.toUiText
import com.djekgrif.weather.feature.home.presentation.model.HomeTab
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(
    getSelectedCity: GetSelectedCityUseCase,
    getTemperatureUnit: GetTemperatureUnitUseCase,
    private val getCurrentWeather: GetCurrentWeatherUseCase,
    private val getWeeklyForecast: GetWeeklyForecastUseCase,
    private val getCurrentCity: GetCurrentCityUseCase,
    private val saveSelectedCity: SaveSelectedCityUseCase,
    connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    private val selectedTab = MutableStateFlow(HomeTab.Today)
    private val refreshTrigger = MutableStateFlow(0)
    private val isRefreshing = MutableStateFlow(false)
    private val isDetectingLocation = MutableStateFlow(false)
    private val locationError = MutableStateFlow<UiText?>(null)

    private val temperatureUnit = getTemperatureUnit()
    // Assume connected until the OS reports otherwise, so combine never stalls waiting for an emit.
    private val isConnected = connectivityObserver.isConnected.onStart { emit(true) }

    private val cityFlow: Flow<String?> = getSelectedCity().distinctUntilChanged()

    private val cityToLoad = combine(cityFlow, refreshTrigger) { city, _ -> city }

    private val weatherFlow: Flow<Result<CurrentWeather, DataError>?> = cityToLoad
        .flatMapLatest { city ->
            if (city == null) {
                flowOf(null)
            } else {
                // Keep the refresh spinner up until the network round-trip finishes (not the
                // instantly-emitted cached value), then clear it.
                getCurrentWeather(city).onCompletion { isRefreshing.value = false }
            }
        }

    private val forecastFlow: Flow<Result<List<DailyForecast>, DataError>?> = cityToLoad
        .flatMapLatest { city ->
            if (city == null) flowOf(null) else getWeeklyForecast(city)
        }

    private val weatherData = combine(weatherFlow, forecastFlow) { weather, forecast ->
        WeatherData(weather, forecast)
    }

    private val locationState = combine(isDetectingLocation, locationError) { detecting, error ->
        LocationState(detecting, error)
    }

    private val uiFlags = combine(isRefreshing, temperatureUnit, isConnected) { refreshing, unit, connected ->
        UiFlags(refreshing, unit, connected)
    }

    val state: StateFlow<HomeUiState> = combine(
        cityFlow,
        selectedTab,
        weatherData,
        locationState,
        uiFlags,
    ) { city, tab, data, location, flags ->
        val weather = (data.weather as? Result.Success)?.data
        val error = (data.weather as? Result.Error)?.error
        val forecast = (data.forecast as? Result.Success)?.data.orEmpty().map { it.toUi(flags.unit) }
        val today = forecast.firstOrNull()
        HomeUiState(
            cityName = weather?.cityName ?: city.orEmpty(),
            selectedTab = tab,
            currentWeather = weather?.toUi(today?.high, today?.low, flags.unit),
            forecast = forecast,
            isLoading = city != null && weather == null && error == null,
            isRefreshing = flags.refreshing,
            errorMessage = error?.toUiText(),
            showLocationPrompt = city == null && !location.isDetecting,
            isDetectingLocation = location.isDetecting,
            locationError = location.error,
            isOffline = !flags.isConnected,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState(isLoading = true),
    )

    fun onTabSelected(tab: HomeTab) {
        selectedTab.value = tab
    }

    fun onRefresh() {
        isRefreshing.value = true
        refreshTrigger.update { it + 1 }
    }

    /** Called once location permission has been granted. */
    fun onUseMyLocation() {
        viewModelScope.launch {
            locationError.value = null
            isDetectingLocation.value = true
            when (val result = getCurrentCity()) {
                is Result.Success -> saveSelectedCity(result.data)
                is Result.Error -> locationError.value = result.error.toUiText()
            }
            isDetectingLocation.value = false
        }
    }

    fun onLocationPermissionDenied() {
        locationError.value = UiText.StringResource(R.string.error_location_permission_denied)
    }

    private data class WeatherData(
        val weather: Result<CurrentWeather, DataError>?,
        val forecast: Result<List<DailyForecast>, DataError>?,
    )

    private data class LocationState(
        val isDetecting: Boolean,
        val error: UiText?,
    )

    private data class UiFlags(
        val refreshing: Boolean,
        val unit: TemperatureUnit,
        val isConnected: Boolean,
    )
}