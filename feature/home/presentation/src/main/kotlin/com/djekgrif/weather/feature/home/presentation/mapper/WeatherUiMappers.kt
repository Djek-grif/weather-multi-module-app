package com.djekgrif.weather.feature.home.presentation.mapper

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
private val timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())

/** Full remote icon URL for an OpenWeatherMap condition code, built here so the UI stays dumb. */
fun weatherIconUrl(iconCode: String): String = "$ICON_BASE_URL/$iconCode@4x.png"

fun CurrentWeather.toUi(
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
        temperature = temperature.toDegrees(),
        feelsLike = feelsLike.toDegrees(),
        description = description.titlecase(),
        iconUrl = weatherIconUrl(iconCode),
        humidity = "$humidity%",
        windSpeed = windSpeed.roundToInt().toString(),
        pressure = pressure.toString(),
        sunrise = formatEpochSeconds(sunrise, cityOffset),
        sunset = formatEpochSeconds(sunset, cityOffset),
        sunProgress = sunProgress,
    )
}

fun DailyForecast.toUi(
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
        iconUrl = weatherIconUrl(iconCode),
        description = description.titlecase(),
        high = tempMax.toDegrees(),
        low = tempMin.toDegrees(),
        isToday = day == today,
    )
}

private fun Double.toDegrees(): String = "${roundToInt()}°"

private fun formatEpochSeconds(epochSeconds: Long, zone: ZoneId): String =
    Instant.ofEpochSecond(epochSeconds).atZone(zone).format(timeFormatter)

private fun String.titlecase(): String = split(" ").joinToString(" ") { word ->
    word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}
