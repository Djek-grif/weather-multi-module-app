package com.djekgrif.weather.feature.home.data.mapper

import com.djekgrif.weather.core.database.entity.CurrentWeatherEntity
import com.djekgrif.weather.core.database.entity.DailyForecastEntity
import com.djekgrif.weather.feature.home.domain.model.CurrentWeather
import com.djekgrif.weather.feature.home.domain.model.DailyForecast

fun CurrentWeather.toEntity(cachedAt: Long): CurrentWeatherEntity = CurrentWeatherEntity(
    cityName = cityName,
    temperature = temperature,
    feelsLike = feelsLike,
    description = description,
    iconCode = iconCode,
    humidity = humidity,
    windSpeed = windSpeed,
    pressure = pressure,
    sunrise = sunrise,
    sunset = sunset,
    timezoneOffsetSeconds = timezoneOffsetSeconds,
    cachedAt = cachedAt,
)

fun CurrentWeatherEntity.toCurrentWeather(): CurrentWeather = CurrentWeather(
    cityName = cityName,
    temperature = temperature,
    feelsLike = feelsLike,
    description = description,
    iconCode = iconCode,
    humidity = humidity,
    windSpeed = windSpeed,
    pressure = pressure,
    sunrise = sunrise,
    sunset = sunset,
    timezoneOffsetSeconds = timezoneOffsetSeconds,
)

fun DailyForecast.toEntity(cityName: String, cachedAt: Long): DailyForecastEntity =
    DailyForecastEntity(
        cityName = cityName,
        date = date,
        tempMin = tempMin,
        tempMax = tempMax,
        description = description,
        iconCode = iconCode,
        humidity = humidity,
        windSpeed = windSpeed,
        cachedAt = cachedAt,
    )

fun DailyForecastEntity.toDailyForecast(): DailyForecast = DailyForecast(
    date = date,
    tempMin = tempMin,
    tempMax = tempMax,
    description = description,
    iconCode = iconCode,
    humidity = humidity,
    windSpeed = windSpeed,
)