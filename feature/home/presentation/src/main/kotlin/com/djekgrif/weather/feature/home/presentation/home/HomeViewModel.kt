package com.djekgrif.weather.feature.home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.core.presentation.mapper.toUiText
import com.djekgrif.weather.feature.home.domain.usecase.GetCurrentWeatherUseCase
import com.djekgrif.weather.feature.home.domain.usecase.GetSelectedCityUseCase
import com.djekgrif.weather.feature.home.domain.usecase.GetWeeklyForecastUseCase
import com.djekgrif.weather.feature.home.presentation.mapper.toUi
import com.djekgrif.weather.feature.home.presentation.model.HomeTab
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(
    getSelectedCity: GetSelectedCityUseCase,
    private val getCurrentWeather: GetCurrentWeatherUseCase,
    private val getWeeklyForecast: GetWeeklyForecastUseCase,
) : ViewModel() {

    private val selectedTab = MutableStateFlow(HomeTab.Today)
    private val refreshTrigger = MutableStateFlow(0)
    private val isRefreshing = MutableStateFlow(false)

    private val cityFlow = getSelectedCity()
        .map { it ?: DEFAULT_CITY }
        .distinctUntilChanged()

    private val cityToLoad = combine(cityFlow, refreshTrigger) { city, _ -> city }

    private val weatherFlow = cityToLoad
        .flatMapLatest { city ->
            // Keep the refresh spinner up until the network round-trip finishes (not the
            // instantly-emitted cached value), then clear it.
            getCurrentWeather(city)
                .onCompletion { isRefreshing.value = false }
        }

    private val forecastFlow = cityToLoad
        .flatMapLatest { city -> getWeeklyForecast(city) }

    val state: StateFlow<HomeUiState> = combine(
        cityFlow,
        selectedTab,
        weatherFlow,
        forecastFlow,
        isRefreshing,
    ) { city, tab, weatherResult, forecastResult, refreshing ->
        val weather = (weatherResult as? Result.Success)?.data
        val error = (weatherResult as? Result.Error)?.error
        val forecast = (forecastResult as? Result.Success)?.data.orEmpty().map { it.toUi() }
        val today = forecast.firstOrNull()
        HomeUiState(
            cityName = weather?.cityName ?: city,
            selectedTab = tab,
            currentWeather = weather?.toUi(),
            forecast = forecast,
            todayHigh = today?.high,
            todayLow = today?.low,
            isLoading = weather == null && error == null,
            isRefreshing = refreshing,
            errorMessage = error?.toUiText(),
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

    private companion object {
        const val DEFAULT_CITY = "San Francisco"
    }
}