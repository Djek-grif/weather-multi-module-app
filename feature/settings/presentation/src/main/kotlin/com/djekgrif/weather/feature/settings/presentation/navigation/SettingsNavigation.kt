package com.djekgrif.weather.feature.settings.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.djekgrif.weather.feature.settings.presentation.settings.SettingsRoot
import kotlinx.serialization.Serializable

@Serializable
data object SettingsRoute

private const val TRANSITION_MS = 320

fun NavGraphBuilder.settingsGraph(
    onBack: () -> Unit,
) {
    composable<SettingsRoute>(
        enterTransition = {
            slideIntoContainer(SlideDirection.Start, tween(TRANSITION_MS)) + fadeIn(tween(TRANSITION_MS))
        },
        popExitTransition = {
            slideOutOfContainer(SlideDirection.End, tween(TRANSITION_MS)) +
                fadeOut(tween(TRANSITION_MS))
        },
    ) {
        SettingsRoot(onBack = onBack)
    }
}
