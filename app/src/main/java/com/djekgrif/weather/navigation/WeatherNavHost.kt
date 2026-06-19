package com.djekgrif.weather.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.djekgrif.weather.feature.home.presentation.navigation.HomeRoute
import com.djekgrif.weather.feature.home.presentation.navigation.homeGraph
import com.djekgrif.weather.feature.search.presentation.navigation.SearchRoute
import com.djekgrif.weather.feature.search.presentation.navigation.searchGraph

@Composable
fun WeatherNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
    ) {
        homeGraph(
            onNavigateToSearch = { navController.navigate(SearchRoute) },
        )
        searchGraph(
            onBack = { navController.popBackStack() },
        )
    }
}