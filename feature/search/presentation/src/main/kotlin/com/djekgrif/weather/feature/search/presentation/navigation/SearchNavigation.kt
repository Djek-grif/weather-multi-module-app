package com.djekgrif.weather.feature.search.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.djekgrif.weather.feature.search.presentation.search.SearchRoot
import kotlinx.serialization.Serializable

@Serializable
data object SearchRoute

private const val TRANSITION_MS = 320

fun NavGraphBuilder.searchGraph(
    onBack: () -> Unit,
) {
    composable<SearchRoute>(
        enterTransition = {
            slideIntoContainer(SlideDirection.Up, tween(TRANSITION_MS)) + fadeIn(tween(TRANSITION_MS))
        },
        popExitTransition = {
            slideOutOfContainer(SlideDirection.Down, tween(TRANSITION_MS)) +
                fadeOut(tween(TRANSITION_MS))
        },
    ) {
        SearchRoot(onBack = onBack)
    }
}