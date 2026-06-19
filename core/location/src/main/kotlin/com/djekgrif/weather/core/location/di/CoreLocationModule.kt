package com.djekgrif.weather.core.location.di

import com.djekgrif.weather.core.domain.location.LocationProvider
import com.djekgrif.weather.core.location.LocationProviderImp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreLocationModule = module {
    single<LocationProvider> { LocationProviderImp(androidContext()) }
}