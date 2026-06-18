package com.djekgrif.weather.feature.home.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.djekgrif.weather.core.designsystem.theme.Dimens
import com.djekgrif.weather.feature.home.presentation.component.WeekRow
import com.djekgrif.weather.feature.home.presentation.model.DailyForecastUi

@Composable
fun WeekScreen(
    forecast: List<DailyForecastUi>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(Dimens.spaceMedium),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp),
    ) {
        items(items = forecast, key = { it.id }) { day ->
            WeekRow(forecast = day)
        }
    }
}