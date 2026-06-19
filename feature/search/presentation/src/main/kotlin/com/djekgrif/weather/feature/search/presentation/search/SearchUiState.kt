package com.djekgrif.weather.feature.search.presentation.search

import androidx.compose.runtime.Immutable
import com.djekgrif.weather.core.presentation.ui.UiText
import com.djekgrif.weather.feature.search.presentation.model.CityUi

@Immutable
data class SearchUiState(
    val query: String = "",
    val results: List<CityUi> = emptyList(),
    val isLoading: Boolean = false,
    val showEmptyState: Boolean = false,
    val errorMessage: UiText? = null,
)