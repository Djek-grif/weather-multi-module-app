package com.djekgrif.weather.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.djekgrif.weather.core.database.dao.WeatherDao
import com.djekgrif.weather.core.database.entity.CurrentWeatherEntity
import com.djekgrif.weather.core.database.entity.DailyForecastEntity

@Database(
    entities = [CurrentWeatherEntity::class, DailyForecastEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        const val DATABASE_NAME = "weather.db"
    }
}