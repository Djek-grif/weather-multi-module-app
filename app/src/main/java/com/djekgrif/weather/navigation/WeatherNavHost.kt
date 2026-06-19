package com.djekgrif.weather.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.djekgrif.weather.feature.home.presentation.navigation.HomeRoute
import com.djekgrif.weather.feature.home.presentation.navigation.homeGraph
import com.djekgrif.weather.feature.search.presentation.navigation.SearchRoute
import com.djekgrif.weather.feature.search.presentation.navigation.searchGraph
import com.djekgrif.weather.feature.settings.presentation.navigation.SettingsRoute
import com.djekgrif.weather.feature.settings.presentation.navigation.settingsGraph

@Composable
fun WeatherNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
    ) {
        homeGraph(
            onNavigateToSearch = { navController.navigate(SearchRoute) },
            onNavigateToSettings = { navController.navigate(SettingsRoute) },
        )
        searchGraph(
            onBack = { navController.popBackStack() },
        )
        settingsGraph(
            onBack = { navController.popBackStack() },
        )
    }
}