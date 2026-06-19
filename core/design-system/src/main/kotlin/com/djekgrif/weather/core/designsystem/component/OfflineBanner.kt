package com.djekgrif.weather.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import com.djekgrif.weather.core.designsystem.R
import com.djekgrif.weather.core.designsystem.theme.Dimens

/** A slim banner telling the user that the displayed data is cached because they're offline. */
@Composable
fun OfflineBanner(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.offline_banner),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(horizontal = Dimens.spaceMedium, vertical = Dimens.spaceSmall)
            .semantics { liveRegion = LiveRegionMode.Polite },
    )
}
