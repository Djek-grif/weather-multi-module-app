package com.djekgrif.weather.core.presentation.permission

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

/**
 * Returns a callback that requests location permission. The UI owns the request; callers react to
 * the grant/denial via [onResult]. Coarse OR fine granted counts as granted.
 */
@Composable
fun rememberLocationPermissionRequester(
    onResult: (granted: Boolean) -> Unit,
): () -> Unit {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        onResult(granted)
    }
    return {
        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ),
        )
    }
}