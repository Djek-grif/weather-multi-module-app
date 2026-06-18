package com.djekgrif.weather.feature.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.djekgrif.weather.core.designsystem.component.WeatherIcon
import com.djekgrif.weather.core.designsystem.theme.Dimens
import com.djekgrif.weather.feature.home.presentation.model.DailyForecastUi

@Composable
fun WeekRow(
    forecast: DailyForecastUi,
    modifier: Modifier = Modifier,
) {
    val containerColor = if (forecast.isToday) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceContainer
    }
    val primaryText = if (forecast.isToday) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    val secondaryText = if (forecast.isToday) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = containerColor),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.spaceMedium),
        ) {
            Text(
                text = forecast.dayName,
                modifier = Modifier.width(52.dp),
                style = MaterialTheme.typography.titleMedium,
                color = primaryText,
            )
            WeatherIcon(
                imageUrl = forecast.iconUrl,
                contentDescription = null,
                size = 34.dp,
            )
            Text(
                text = forecast.description,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodySmall,
                color = secondaryText,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimens.spaceSmall),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = forecast.high,
                    style = MaterialTheme.typography.titleMedium,
                    color = primaryText,
                    textAlign = TextAlign.End,
                )
                Text(
                    text = forecast.low,
                    style = MaterialTheme.typography.bodyMedium,
                    color = secondaryText,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}