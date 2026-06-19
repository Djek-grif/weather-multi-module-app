package com.djekgrif.weather.feature.settings.presentation.di

import com.djekgrif.weather.feature.settings.presentation.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsPresentationModule = module {
    viewModelOf(::SettingsViewModel)
}
