package com.djekgrif.weather.core.database.di

import androidx.room.Room
import com.djekgrif.weather.core.database.WeatherDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            WeatherDatabase::class.java,
            WeatherDatabase.DATABASE_NAME,
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }
    single { get<WeatherDatabase>().weatherDao() }
}