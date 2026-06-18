package com.djekgrif.weather.feature.home.data.local

import com.djekgrif.weather.core.database.dao.WeatherDao
import com.djekgrif.weather.feature.home.data.mapper.toCurrentWeather
import com.djekgrif.weather.feature.home.data.mapper.toDailyForecast
import com.djekgrif.weather.feature.home.data.mapper.toEntity
import com.djekgrif.weather.feature.home.domain.model.CurrentWeather
import com.djekgrif.weather.feature.home.domain.model.DailyForecast

class WeatherLocalDataSourceImp(
    private val weatherDao: WeatherDao,
) : WeatherLocalDataSource {

    override suspend fun getCurrentWeather(city: String): CurrentWeather? =
        weatherDao.getCurrentWeather(city)?.toCurrentWeather()

    override suspend fun upsertCurrentWeather(weather: CurrentWeather, cachedAt: Long) {
        weatherDao.upsertCurrentWeather(weather.toEntity(cachedAt))
    }

    override suspend fun getForecast(city: String): List<DailyForecast> =
        weatherDao.getForecast(city).map { it.toDailyForecast() }

    override suspend fun replaceForecast(
        city: String,
        forecast: List<DailyForecast>,
        cachedAt: Long,
    ) {
        weatherDao.replaceForecast(city, forecast.map { it.toEntity(city, cachedAt) })
    }
}