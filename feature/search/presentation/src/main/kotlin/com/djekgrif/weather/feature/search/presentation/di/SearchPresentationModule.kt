package com.djekgrif.weather.feature.search.presentation.di

import com.djekgrif.weather.feature.search.domain.usecase.SearchCitiesUseCase
import com.djekgrif.weather.feature.search.domain.usecase.SelectCityUseCase
import com.djekgrif.weather.feature.search.presentation.search.SearchViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val searchPresentationModule = module {
    factoryOf(::SearchCitiesUseCase)
    factoryOf(::SelectCityUseCase)

    viewModelOf(::SearchViewModel)
}