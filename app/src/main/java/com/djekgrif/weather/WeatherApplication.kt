package com.djekgrif.weather

import android.app.Application
import com.djekgrif.weather.core.data.di.coreDataModule
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
                coreDataModule,
                // Feature modules are registered here as later stages add them.
            )
        }
    }
}