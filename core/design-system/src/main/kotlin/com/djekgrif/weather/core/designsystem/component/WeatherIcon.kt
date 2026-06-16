package com.djekgrif.weather.core.designsystem.component

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.djekgrif.weather.core.designsystem.theme.Dimens

/**
 * Renders a remote weather icon. The caller passes a fully-formed [imageUrl];
 * this component contains no URL-building logic.
 */
@Composable
fun WeatherIcon(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = Dimens.weatherIconLarge,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier.size(size),
    )
}