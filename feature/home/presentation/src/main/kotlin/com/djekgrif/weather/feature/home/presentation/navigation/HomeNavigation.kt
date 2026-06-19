package com.djekgrif.weather.feature.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.djekgrif.weather.feature.home.presentation.home.HomeRoot
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeGraph(
    onNavigateToSearch: () -> Unit,
    onNavigateToSettings: () -> Unit,
) {
    composable<HomeRoute> {
        HomeRoot(
            onNavigateToSearch = onNavigateToSearch,
            onNavigateToSettings = onNavigateToSettings,
        )
    }
}