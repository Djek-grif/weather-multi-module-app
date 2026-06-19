package com.djekgrif.weather.feature.home.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.djekgrif.weather.core.designsystem.component.ErrorState
import com.djekgrif.weather.core.designsystem.component.LoadingIndicator
import com.djekgrif.weather.core.designsystem.theme.Dimens
import com.djekgrif.weather.core.designsystem.theme.WeatherTheme
import com.djekgrif.weather.core.presentation.permission.rememberLocationPermissionRequester
import com.djekgrif.weather.feature.home.presentation.R
import com.djekgrif.weather.feature.home.presentation.component.LocationPrompt
import com.djekgrif.weather.feature.home.presentation.model.CurrentWeatherUi
import com.djekgrif.weather.feature.home.presentation.model.DailyForecastUi
import com.djekgrif.weather.feature.home.presentation.model.HomeTab
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoot(
    onNavigateToSearch: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val requestLocation = rememberLocationPermissionRequester { granted ->
        if (granted) viewModel.onUseMyLocation() else viewModel.onLocationPermissionDenied()
    }
    HomeScreen(
        state = state,
        onTabSelected = viewModel::onTabSelected,
        onRefresh = viewModel::onRefresh,
        onSearchClick = onNavigateToSearch,
        onUseMyLocation = requestLocation,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeUiState,
    onTabSelected: (HomeTab) -> Unit,
    onRefresh: () -> Unit,
    onSearchClick: () -> Unit,
    onUseMyLocation: () -> Unit,
) {
    Scaffold(
        topBar = { HomeTopBar(city = state.cityName, onSearchClick = onSearchClick) },
        bottomBar = { HomeBottomBar(selectedTab = state.selectedTab, onTabSelected = onTabSelected) },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            when {
                state.isDetectingLocation -> LoadingIndicator()

                state.showLocationPrompt -> LocationPrompt(
                    errorMessage = state.locationError?.asString(),
                    onUseMyLocation = onUseMyLocation,
                    onSearch = onSearchClick,
                )

                state.isLoading -> LoadingIndicator()

                state.currentWeather == null && state.errorMessage != null -> ErrorState(
                    message = state.errorMessage.asString(),
                    onRetry = onRefresh,
                )

                state.currentWeather != null -> {
                    PullToRefreshBox(
                        isRefreshing = state.isRefreshing,
                        onRefresh = onRefresh,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        when (state.selectedTab) {
                            HomeTab.Today -> TodayScreen(weather = state.currentWeather)
                            HomeTab.Week -> WeekScreen(forecast = state.forecast)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(
    city: String,
    onSearchClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.spaceExtraSmall),
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = stringResource(R.string.cd_location),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp),
                )
                Text(text = city, style = MaterialTheme.typography.titleLarge)
            }
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.cd_search),
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    )
}

@Composable
private fun HomeBottomBar(
    selectedTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit,
) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
        NavigationBarItem(
            selected = selectedTab == HomeTab.Today,
            onClick = { onTabSelected(HomeTab.Today) },
            icon = { Icon(Icons.Filled.WbSunny, contentDescription = null) },
            label = { Text(stringResource(R.string.tab_today)) },
        )
        NavigationBarItem(
            selected = selectedTab == HomeTab.Week,
            onClick = { onTabSelected(HomeTab.Week) },
            icon = { Icon(Icons.Filled.CalendarMonth, contentDescription = null) },
            label = { Text(stringResource(R.string.tab_week)) },
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    WeatherTheme {
        HomeScreen(
            state = HomeUiState(
                cityName = "San Francisco",
                currentWeather = CurrentWeatherUi(
                    cityName = "San Francisco",
                    temperature = "18°",
                    highTemperature = "18°",
                    lowTemperature = "10°",
                    feelsLike = "17°",
                    description = "Partly Cloudy",
                    iconUrl = "",
                    humidity = "72%",
                    windSpeed = "5",
                    pressure = "1014",
                    sunrise = "5:48 AM",
                    sunset = "8:32 PM",
                    sunProgress = 0.54f,
                ),
                forecast = List(5) {
                    DailyForecastUi(
                        id = it.toLong(),
                        dayName = if (it == 0) "Today" else "Day $it",
                        iconUrl = "",
                        description = "Partly cloudy",
                        high = "${18 + it}°",
                        low = "${10 + it}°",
                        isToday = it == 0,
                    )
                },
            ),
            onTabSelected = {},
            onRefresh = {},
            onSearchClick = {},
            onUseMyLocation = {},
        )
    }
}