package com.djekgrif.weather.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.djekgrif.weather.core.designsystem.modifier.shimmer
import com.djekgrif.weather.core.designsystem.theme.Dimens

/** Placeholder that mimics the Today layout (hero card + stat row + sun-path card) while loading. */
@Composable
fun WeatherLoadingSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(Dimens.spaceMedium),
    ) {
        SkeletonBlock(modifier = Modifier.fillMaxWidth().height(300.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spaceMedium)) {
            repeat(3) {
                SkeletonBlock(modifier = Modifier.weight(1f).height(96.dp))
            }
        }
        SkeletonBlock(modifier = Modifier.fillMaxWidth().height(140.dp))
    }
}

@Composable
private fun SkeletonBlock(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.spaceMedium))
            .shimmer(),
    )
}
