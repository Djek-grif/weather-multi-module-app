package com.djekgrif.weather.feature.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.djekgrif.weather.core.designsystem.theme.Dimens
import com.djekgrif.weather.core.designsystem.theme.WeatherTheme
import com.djekgrif.weather.feature.home.presentation.R
import com.djekgrif.weather.feature.home.presentation.model.CurrentWeatherUi

@Composable
fun StatsRow(
    weather: CurrentWeatherUi,
    modifier: Modifier = Modifier,
) {
    val weatherColors = WeatherTheme.weatherColors
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimens.spaceMedium),
    ) {
        HomeWeatherStatChip(
            icon = Icons.Filled.WaterDrop,
            tint = weatherColors.statHumidity,
            value = weather.humidity,
            label = stringResource(R.string.stat_humidity_label),
            modifier = Modifier.weight(1f),
        )
        HomeWeatherStatChip(
            icon = Icons.Filled.Air,
            tint = weatherColors.statWind,
            value = weather.windSpeed,
            label = stringResource(R.string.stat_wind_label),
            modifier = Modifier.weight(1f),
        )
        HomeWeatherStatChip(
            icon = Icons.Filled.Compress,
            tint = weatherColors.statPressure,
            value = weather.pressure,
            label = stringResource(R.string.stat_pressure_label),
            modifier = Modifier.weight(1f),
        )
    }
}