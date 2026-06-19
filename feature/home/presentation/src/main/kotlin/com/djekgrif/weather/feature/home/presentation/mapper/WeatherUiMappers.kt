package com.djekgrif.weather.feature.home.presentation.mapper

import com.djekgrif.weather.core.domain.settings.TemperatureUnit
import com.djekgrif.weather.feature.home.domain.model.CurrentWeather
import com.djekgrif.weather.feature.home.domain.model.DailyForecast
import com.djekgrif.weather.feature.home.presentation.model.CurrentWeatherUi
import com.djekgrif.weather.feature.home.presentation.model.DailyForecastUi
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

private const val ICON_BASE_URL = "https://openweathermap.org/img/wn"
private const val MS_TO_MPH = 2.23694
private val timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())

/** Full remote icon URL for an OpenWeatherMap condition code, built here so the UI stays dumb. */
fun weatherIconUrl(iconCode: String): String = "$ICON_BASE_URL/$iconCode@4x.png"

fun CurrentWeather.toUi(
    highTemperature: String?,
    lowTemperature: String?,
    unit: TemperatureUnit,
    now: Long = System.currentTimeMillis(),
): CurrentWeatherUi {
    val sunProgress = if (sunset > sunrise) {
        val nowSeconds = now / 1000.0
        ((nowSeconds - sunrise) / (sunset - sunrise)).coerceIn(0.0, 1.0).toFloat()
    } else {
        0f
    }
    // Render sunrise/sunset in the CITY's local time, not the device's.
    val cityOffset = ZoneOffset.ofTotalSeconds(timezoneOffsetSeconds)
    return CurrentWeatherUi(
        cityName = cityName,
        temperature = temperature.toTemperature(unit),
        highTemperature = highTemperature ?: "",
        lowTemperature = lowTemperature ?: "",
        feelsLike = feelsLike.toTemperature(unit),
        description = description.titlecase(),
        iconUrl = weatherIconUrl(iconCode),
        humidity = "$humidity%",
        windSpeed = windSpeed.toWind(unit),
        pressure = pressure.toString(),
        sunrise = formatEpochSeconds(sunrise, cityOffset),
        sunset = formatEpochSeconds(sunset, cityOffset),
        sunProgress = sunProgress,
        lastUpdatedLabel = lastUpdated?.let { formatRelative(it, now) },
    )
}

fun DailyForecast.toUi(
    unit: TemperatureUnit,
    now: Long = System.currentTimeMillis(),
    zone: ZoneId = ZoneId.systemDefault(),
): DailyForecastUi {
    val day = Instant.ofEpochMilli(date).atZone(zone).toLocalDate()
    val today = Instant.ofEpochMilli(now).atZone(zone).toLocalDate()
    return DailyForecastUi(
        id = date,
        dayName = if (day == today) {
            "Today"
        } else {
            day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
        },
        // A daily summary always shows the day-time icon variant (…d), never the night (…n) one.
        iconUrl = weatherIconUrl(iconCode.toDayIcon()),
        description = description.titlecase(),
        high = tempMax.toTemperature(unit),
        low = tempMin.toTemperature(unit),
        isToday = day == today,
    )
}

/** OpenWeatherMap icon codes end in `d` (day) or `n` (night); force the day variant. */
private fun String.toDayIcon(): String = if (endsWith("n")) "${dropLast(1)}d" else this

/** Domain temps are always fetched in Celsius (metric); convert client-side for display. */
private fun Double.toTemperature(unit: TemperatureUnit): String = when (unit) {
    TemperatureUnit.CELSIUS -> "${roundToInt()}°C"
    TemperatureUnit.FAHRENHEIT -> "${(this * 9 / 5 + 32).roundToInt()}°F"
}

/** Domain wind is always in m/s (metric); convert to mph for the imperial unit. */
private fun Double.toWind(unit: TemperatureUnit): String = when (unit) {
    TemperatureUnit.CELSIUS -> "${roundToInt()} m/s"
    TemperatureUnit.FAHRENHEIT -> "${(this * MS_TO_MPH).roundToInt()} mph"
}

private fun formatEpochSeconds(epochSeconds: Long, zone: ZoneId): String =
    Instant.ofEpochSecond(epochSeconds).atZone(zone).format(timeFormatter)

/** Relative "Updated …" label for the last refresh time. */
private fun formatRelative(epochMillis: Long, now: Long): String {
    val minutes = (now - epochMillis).coerceAtLeast(0) / 60_000
    return when {
        minutes < 1 -> "Updated just now"
        minutes < 60 -> "Updated ${minutes}m ago"
        minutes < 60 * 24 -> "Updated ${minutes / 60}h ago"
        else -> "Updated ${minutes / (60 * 24)}d ago"
    }
}

private fun String.titlecase(): String = split(" ").joinToString(" ") { word ->
    word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}
