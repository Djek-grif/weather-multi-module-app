package com.djekgrif.weather.feature.home.presentation.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.djekgrif.weather.core.designsystem.theme.Dimens
import com.djekgrif.weather.core.designsystem.theme.WeatherTheme
import com.djekgrif.weather.feature.home.presentation.R
import com.djekgrif.weather.feature.home.presentation.model.CurrentWeatherUi
import kotlin.math.roundToInt

// Sun-path arc viewBox from the design (M6 52 Q150 -18 294 52).
private const val ARC_VIEW_W = 300f
private const val ARC_VIEW_H = 56f

@Composable
fun SunPathCard(
    weather: CurrentWeatherUi,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Column(modifier = Modifier.padding(Dimens.spaceMedium)) {
            SunArc(
                progress = weather.sunProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ARC_VIEW_W / ARC_VIEW_H),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.spaceMedium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                SunLabel(
                    label = stringResource(R.string.sunrise),
                    time = weather.sunrise,
                    tint = WeatherTheme.weatherColors.sunrise,
                    iconLeading = true,
                    alignment = Alignment.Start,
                )
                SunLabel(
                    label = stringResource(R.string.sunset),
                    time = weather.sunset,
                    tint = WeatherTheme.weatherColors.sunset,
                    iconLeading = false,
                    alignment = Alignment.End,
                )
            }
        }
    }
}

@Composable
private fun SunArc(progress: Float, modifier: Modifier = Modifier) {
    val weatherColors = WeatherTheme.weatherColors
    val track = MaterialTheme.colorScheme.outlineVariant
    val pulse = rememberInfiniteTransition(label = "sun-pulse")
    val pulseScale by pulse.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(tween(2800), RepeatMode.Reverse),
        label = "sun-pulse-scale",
    )
    val t = progress.coerceIn(0f, 1f)
    val sunSize = 24.dp

    BoxWithConstraints(modifier) {
        val w = constraints.maxWidth.toFloat()
        val h = constraints.maxHeight.toFloat()
        val p0 = Offset(6f / ARC_VIEW_W * w, 52f / ARC_VIEW_H * h)
        val control = Offset(150f / ARC_VIEW_W * w, -18f / ARC_VIEW_H * h)
        val p1 = Offset(294f / ARC_VIEW_W * w, 52f / ARC_VIEW_H * h)
        val sun = quadraticBezier(p0, control, p1, t)

        Canvas(Modifier.matchParentSize()) {
            val path = Path().apply {
                moveTo(p0.x, p0.y)
                quadraticTo(control.x, control.y, p1.x, p1.y)
            }
            drawPath(
                path = path,
                color = track,
                style = Stroke(
                    width = 2.dp.toPx(),
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(2.dp.toPx(), 6.dp.toPx()),
                    ),
                ),
            )
            val measure = PathMeasure().apply { setPath(path, forceClosed = false) }
            val filled = Path()
            measure.getSegment(0f, measure.length * t, filled, startWithMoveTo = true)
            drawPath(
                path = filled,
                color = weatherColors.sun,
                style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round),
            )
            drawCircle(
                color = weatherColors.sunGlow,
                radius = 11.dp.toPx() * pulseScale,
                center = sun,
            )
        }

        Icon(
            imageVector = Icons.Filled.WbSunny,
            contentDescription = null,
            tint = weatherColors.sun,
            modifier = Modifier
                .size(sunSize)
                .offset {
                    IntOffset(
                        (sun.x - sunSize.toPx() / 2f).roundToInt(),
                        (sun.y - sunSize.toPx() / 2f).roundToInt(),
                    )
                }
                .graphicsLayer {
                    scaleX = pulseScale
                    scaleY = pulseScale
                },
        )
    }
}

@Composable
private fun SunLabel(
    label: String,
    time: String,
    tint: Color,
    iconLeading: Boolean,
    alignment: Alignment.Horizontal,
) {
    Column(
        horizontalAlignment = alignment,
        verticalArrangement = Arrangement.spacedBy(Dimens.spaceExtraSmall),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            if (iconLeading) {
                TwilightIcon(tint = tint, flipped = false)
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            } else {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                TwilightIcon(tint = tint, flipped = true)
            }
        }
        Text(
            text = time,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun TwilightIcon(tint: Color, flipped: Boolean) {
    Icon(
        imageVector = Icons.Filled.WbTwilight,
        contentDescription = null,
        tint = tint,
        modifier = Modifier
            .size(20.dp)
            .graphicsLayer { if (flipped) scaleY = -1f },
    )
}

private fun quadraticBezier(p0: Offset, control: Offset, p1: Offset, t: Float): Offset {
    val mt = 1f - t
    return Offset(
        x = mt * mt * p0.x + 2f * mt * t * control.x + t * t * p1.x,
        y = mt * mt * p0.y + 2f * mt * t * control.y + t * t * p1.y,
    )
}