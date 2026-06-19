package com.djekgrif.weather.feature.home.data.di

import com.djekgrif.weather.feature.home.data.local.WeatherLocalDataSourceImp
import com.djekgrif.weather.feature.home.data.local.WeatherLocalDataSource
import com.djekgrif.weather.feature.home.data.remote.LocationRemoteDataSource
import com.djekgrif.weather.feature.home.data.remote.LocationRemoteDataSourceImp
import com.djekgrif.weather.feature.home.data.remote.WeatherRemoteDataSourceImp
import com.djekgrif.weather.feature.home.data.remote.WeatherRemoteDataSource
import com.djekgrif.weather.feature.home.data.repository.LocationRepositoryImp
import com.djekgrif.weather.feature.home.data.repository.WeatherRepositoryImp
import com.djekgrif.weather.feature.home.domain.repository.LocationRepository
import com.djekgrif.weather.feature.home.domain.repository.WeatherRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val homeDataModule = module {
    singleOf(::WeatherRemoteDataSourceImp) { bind<WeatherRemoteDataSource>() }
    singleOf(::WeatherLocalDataSourceImp) { bind<WeatherLocalDataSource>() }
    // Explicit construction so WeatherRepositoryImp's default `now` time source is used.
    single<WeatherRepository> { WeatherRepositoryImp(get(), get(), get(), get()) }

    singleOf(::LocationRemoteDataSourceImp) { bind<LocationRemoteDataSource>() }
    singleOf(::LocationRepositoryImp) { bind<LocationRepository>() }
}