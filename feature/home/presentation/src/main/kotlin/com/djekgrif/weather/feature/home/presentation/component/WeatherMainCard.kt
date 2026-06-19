package com.djekgrif.weather.feature.home.presentation.component

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.djekgrif.weather.core.designsystem.component.WeatherIcon
import com.djekgrif.weather.core.designsystem.theme.Dimens
import com.djekgrif.weather.core.designsystem.theme.WeatherTheme
import com.djekgrif.weather.feature.home.presentation.R
import com.djekgrif.weather.feature.home.presentation.model.CurrentWeatherUi

@Composable
fun WeatherMainCard(
    weather: CurrentWeatherUi,
    modifier: Modifier = Modifier,
) {
    val weatherColors = WeatherTheme.weatherColors
    val floatTransition = rememberInfiniteTransition(label = "hero-float")
    val floatY by floatTransition.animateFloat(
        initialValue = 0f,
        targetValue = -18f,
        animationSpec = infiniteRepeatable(tween(1800, easing = EaseInOut), RepeatMode.Reverse),
        label = "hero-float-y",
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        listOf(weatherColors.heroGradientStart, weatherColors.heroGradientEnd),
                    ),
                )
                .fillMaxWidth()
                .padding(horizontal = Dimens.spaceLarge, vertical = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.spaceSmall),
        ) {
            WeatherIcon(
                imageUrl = weather.iconUrl,
                contentDescription = stringResource(R.string.cd_weather_icon),
                modifier = Modifier.graphicsLayer { translationY = floatY.dp.toPx() },
                size = 140.dp,
            )
            Text(
                modifier = Modifier.padding(start = Dimens.spaceLarge),
                text = weather.temperature,
                style = MaterialTheme.typography.displayLarge,
                color = weatherColors.heroText
            )
            Text(
                text = weather.description,
                style = MaterialTheme.typography.titleLarge,
                color = weatherColors.heroText,
            )
            Text(
                text = stringResource(R.string.feels_like, weather.feelsLike),
                style = MaterialTheme.typography.bodyLarge,
                color = weatherColors.heroSubText,
            )
            if (weather.highTemperature.isNotBlank() &&  weather.lowTemperature.isNotBlank()) {
                val string = stringResource(
                    R.string.max_temp,
                    weather.highTemperature,
                    weather.lowTemperature,
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = Dimens.spaceSmall),
                    color = weatherColors.heroDivider,
                )
                Row {
                    Text(
                        text = stringResource(R.string.max_temp, weather.highTemperature),
                        style = MaterialTheme.typography.titleSmall,
                        color = weatherColors.heroSubText,
                    )
                    Spacer(modifier = Modifier.width(Dimens.spaceMedium))
                    Text(
                        text = stringResource(R.string.min_temp, weather.lowTemperature),
                        style = MaterialTheme.typography.titleSmall,
                        color = weatherColors.heroSubText,
                    )
                }
            }
        }
    }
}