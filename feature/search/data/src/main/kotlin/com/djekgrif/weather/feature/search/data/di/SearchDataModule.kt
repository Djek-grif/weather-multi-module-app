package com.djekgrif.weather.feature.search.data.di

import com.djekgrif.weather.feature.search.data.remote.CityRemoteDataSource
import com.djekgrif.weather.feature.search.data.remote.CityRemoteDataSourceImp
import com.djekgrif.weather.feature.search.data.repository.CityRepositoryImp
import com.djekgrif.weather.feature.search.domain.repository.CityRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val searchDataModule = module {
    singleOf(::CityRemoteDataSourceImp) { bind<CityRemoteDataSource>() }
    singleOf(::CityRepositoryImp) { bind<CityRepository>() }
}