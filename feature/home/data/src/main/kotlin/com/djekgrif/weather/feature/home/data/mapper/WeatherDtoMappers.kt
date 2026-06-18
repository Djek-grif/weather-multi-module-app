package com.djekgrif.weather.feature.home.data.mapper

import com.djekgrif.weather.feature.home.data.remote.dto.ForecastResponseDto
import com.djekgrif.weather.feature.home.data.remote.dto.WeatherResponseDto
import com.djekgrif.weather.feature.home.domain.model.CurrentWeather
import com.djekgrif.weather.feature.home.domain.model.DailyForecast
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import kotlin.math.abs
import kotlin.math.roundToInt

fun WeatherResponseDto.toCurrentWeather(): CurrentWeather {
    val condition = weather.firstOrNull()
    return CurrentWeather(
        cityName = name,
        temperature = main.temp,
        feelsLike = main.feelsLike,
        description = condition?.description.orEmpty(),
        iconCode = condition?.icon.orEmpty(),
        humidity = main.humidity,
        windSpeed = wind.speed,
        pressure = main.pressure,
        sunrise = sys.sunrise,
        sunset = sys.sunset,
        timezoneOffsetSeconds = timezone,
    )
}

/**
 * Aggregates the API's 3-hour forecast slots into one [DailyForecast] per day:
 * min/max temps span the day, and the slot closest to midday represents the day's icon/description.
 */
fun ForecastResponseDto.toDailyForecasts(zoneId: ZoneId = ZoneOffset.UTC): List<DailyForecast> {
    return list
        .groupBy { item -> Instant.ofEpochSecond(item.dt).atZone(zoneId).toLocalDate() }
        .map { (date, items) ->
            val representative = items.minByOrNull { item ->
                abs(Instant.ofEpochSecond(item.dt).atZone(zoneId).hour - 12)
            } ?: items.first()
            val condition = representative.weather.firstOrNull()
            DailyForecast(
                date = date.atStartOfDay(zoneId).toInstant().toEpochMilli(),
                tempMin = items.minOf { it.main.tempMin },
                tempMax = items.maxOf { it.main.tempMax },
                description = condition?.description.orEmpty(),
                iconCode = condition?.icon.orEmpty(),
                humidity = items.map { it.main.humidity }.average().roundToInt(),
                windSpeed = items.map { it.wind.speed }.average(),
            )
        }
        .sortedBy { it.date }
}