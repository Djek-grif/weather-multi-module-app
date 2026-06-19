package com.djekgrif.weather.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Weather-specific brand accents that sit outside the Material 3 color roles.
 * Access via [com.djekgrif.weather.core.designsystem.theme.WeatherTheme.weatherColors].
 */
@Immutable
data class WeatherColors(
    val statHumidity: Color,
    val statWind: Color,
    val statPressure: Color,
    val sunrise: Color,
    val sunset: Color,
    val sun: Color,
    val heroGradientStart: Color,
    val heroGradientEnd: Color,
    val heroText: Color,
    val heroSubText: Color,
    val heroDivider: Color,
)

val LightWeatherColors = WeatherColors(
    statHumidity = Color(0xFF2986CC),
    statWind = Color(0xFF3A8C7E),
    statPressure = Color(0xFF7A6CC9),
    sunrise = Color(0xFFE8961E),
    sunset = Color(0xFFC2540E),
    sun = Color(0xFFF0A938),
    heroGradientStart = Color(0xFF2274BE),
    heroGradientEnd = Color(0xFF0B4D87),
    heroText = Color(0xFFFFFFFF),
    heroSubText = Color(0xFFCFE3FF),
    heroDivider = Color(0x38FFFFFF),
)

val DarkWeatherColors = WeatherColors(
    statHumidity = Color(0xFF6FB8EC),
    statWind = Color(0xFF74C7B6),
    statPressure = Color(0xFFB3A6F0),
    sunrise = Color(0xFFF0B45A),
    sunset = Color(0xFFE08A4A),
    sun = Color(0xFFF0A938),
    heroGradientStart = Color(0xFF1A5A8C),
    heroGradientEnd = Color(0xFF0B3556),
    heroText = Color(0xFFEAF2FF),
    heroSubText = Color(0xFFA9CBF0),
    heroDivider = Color(0x29FFFFFF),
)

val LocalWeatherColors = staticCompositionLocalOf { LightWeatherColors }
