package com.djekgrif.weather.feature.home.presentation.di

import com.djekgrif.weather.feature.home.domain.usecase.GetCurrentCityUseCase
import com.djekgrif.weather.feature.home.domain.usecase.GetCurrentWeatherUseCase
import com.djekgrif.weather.feature.home.domain.usecase.GetSelectedCityUseCase
import com.djekgrif.weather.feature.home.domain.usecase.GetWeeklyForecastUseCase
import com.djekgrif.weather.feature.home.domain.usecase.SaveSelectedCityUseCase
import com.djekgrif.weather.feature.home.presentation.home.HomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homePresentationModule = module {
    factoryOf(::GetCurrentWeatherUseCase)
    factoryOf(::GetWeeklyForecastUseCase)
    factoryOf(::GetSelectedCityUseCase)
    factoryOf(::SaveSelectedCityUseCase)
    factoryOf(::GetCurrentCityUseCase)

    viewModelOf(::HomeViewModel)
}