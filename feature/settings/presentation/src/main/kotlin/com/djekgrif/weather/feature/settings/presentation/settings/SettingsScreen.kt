package com.djekgrif.weather.feature.settings.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.djekgrif.weather.core.designsystem.theme.Dimens
import com.djekgrif.weather.core.designsystem.theme.WeatherTheme
import com.djekgrif.weather.core.domain.settings.TemperatureUnit
import com.djekgrif.weather.feature.settings.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsRoot(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SettingsScreen(
        state = state,
        onUnitSelected = viewModel::onUnitSelected,
        onBack = onBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsUiState,
    onUnitSelected: (TemperatureUnit) -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_back),
                        )
                    }
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Dimens.spaceMedium),
        ) {
            Text(
                text = stringResource(R.string.settings_units_label),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(
                    horizontal = Dimens.spaceSmall,
                    vertical = Dimens.spaceSmall,
                ),
            )
            Column(modifier = Modifier.selectableGroup()) {
                UnitRow(
                    label = stringResource(R.string.unit_celsius),
                    selected = state.selectedUnit == TemperatureUnit.CELSIUS,
                    onClick = { onUnitSelected(TemperatureUnit.CELSIUS) },
                )
                UnitRow(
                    label = stringResource(R.string.unit_fahrenheit),
                    selected = state.selectedUnit == TemperatureUnit.FAHRENHEIT,
                    onClick = { onUnitSelected(TemperatureUnit.FAHRENHEIT) },
                )
            }
        }
    }
}

@Composable
private fun UnitRow(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(selected = selected, onClick = onClick, role = Role.RadioButton)
            .padding(horizontal = Dimens.spaceSmall, vertical = Dimens.spaceMedium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = selected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = Dimens.spaceMedium),
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    WeatherTheme {
        SettingsScreen(
            state = SettingsUiState(selectedUnit = TemperatureUnit.CELSIUS),
            onUnitSelected = {},
            onBack = {},
        )
    }
}
