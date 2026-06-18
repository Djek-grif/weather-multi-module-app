package com.djekgrif.weather

import android.app.Application
import com.djekgrif.weather.core.data.di.coreDataModule
import com.djekgrif.weather.core.database.di.coreDatabaseModule
import com.djekgrif.weather.feature.home.data.di.homeDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@WeatherApplication)
            modules(
                // core
                coreDataModule,
                coreDatabaseModule,
                // features
                homeDataModule,
            )
        }
    }
}