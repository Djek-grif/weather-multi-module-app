package com.djekgrif.weather.feature.home.data.di

import com.djekgrif.weather.feature.home.data.local.WeatherLocalDataSourceImp
import com.djekgrif.weather.feature.home.data.local.WeatherLocalDataSource
import com.djekgrif.weather.feature.home.data.remote.WeatherRemoteDataSourceImp
import com.djekgrif.weather.feature.home.data.remote.WeatherRemoteDataSource
import com.djekgrif.weather.feature.home.data.repository.WeatherRepositoryImp
import com.djekgrif.weather.feature.home.domain.repository.WeatherRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val homeDataModule = module {
    singleOf(::WeatherRemoteDataSourceImp) { bind<WeatherRemoteDataSource>() }
    singleOf(::WeatherLocalDataSourceImp) { bind<WeatherLocalDataSource>() }
    singleOf(::WeatherRepositoryImp) { bind<WeatherRepository>() }
}