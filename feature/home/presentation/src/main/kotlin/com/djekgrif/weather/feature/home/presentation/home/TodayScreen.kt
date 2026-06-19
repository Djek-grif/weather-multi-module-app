package com.djekgrif.weather.feature.home.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.djekgrif.weather.core.designsystem.theme.Dimens
import com.djekgrif.weather.feature.home.presentation.component.StatsRow
import com.djekgrif.weather.feature.home.presentation.component.SunPathCard
import com.djekgrif.weather.feature.home.presentation.component.WeatherMainCard
import com.djekgrif.weather.feature.home.presentation.model.CurrentWeatherUi

@Composable
fun TodayScreen(
    weather: CurrentWeatherUi,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Dimens.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(Dimens.spaceMedium),
    ) {
        WeatherMainCard(weather = weather)
        StatsRow(weather = weather)
        SunPathCard(weather = weather)
    }
}