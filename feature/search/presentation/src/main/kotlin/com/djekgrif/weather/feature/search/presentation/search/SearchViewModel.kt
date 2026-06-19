package com.djekgrif.weather.feature.search.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.core.presentation.mapper.toUiText
import com.djekgrif.weather.core.presentation.ui.UiText
import com.djekgrif.weather.feature.search.domain.model.City
import com.djekgrif.weather.feature.search.domain.usecase.SearchCitiesUseCase
import com.djekgrif.weather.feature.search.domain.usecase.SelectCityUseCase
import com.djekgrif.weather.feature.search.presentation.mapper.toUi
import com.djekgrif.weather.feature.search.presentation.model.CityUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchViewModel(
    private val searchCities: SearchCitiesUseCase,
    private val selectCity: SelectCityUseCase,
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")

    private val navigateBackChannel = Channel<Unit>()
    val navigateBack: Flow<Unit> = navigateBackChannel.receiveAsFlow()

    private val searchResults: Flow<SearchResults> = queryFlow
        .debounce(SEARCH_DEBOUNCE_MS)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            val trimmed = query.trim()
            if (trimmed.length < MIN_QUERY_LENGTH) {
                flowOf(SearchResults())
            } else {
                flow {
                    emit(SearchResults(isLoading = true))
                    emit(searchCities(trimmed).toSearchResults())
                }
            }
        }

    val state: StateFlow<SearchUiState> = combine(queryFlow, searchResults) { query, results ->
        SearchUiState(
            query = query,
            results = results.cities,
            isLoading = results.isLoading,
            showEmptyState = query.trim().length >= MIN_QUERY_LENGTH &&
                !results.isLoading &&
                results.error == null &&
                results.cities.isEmpty(),
            errorMessage = results.error,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchUiState(),
    )

    fun onQueryChange(query: String) {
        queryFlow.value = query
    }

    fun onClearQuery() {
        queryFlow.value = ""
    }

    fun onCitySelected(city: CityUi) {
        viewModelScope.launch {
            selectCity(city.name)
            navigateBackChannel.send(Unit)
        }
    }

    private fun Result<List<City>, DataError>.toSearchResults(): SearchResults = when (this) {
        is Result.Success -> SearchResults(cities = data.map { it.toUi() })
        is Result.Error -> SearchResults(error = error.toUiText())
    }

    private data class SearchResults(
        val cities: List<CityUi> = emptyList(),
        val isLoading: Boolean = false,
        val error: UiText? = null,
    )

    private companion object {
        const val SEARCH_DEBOUNCE_MS = 300L
        const val MIN_QUERY_LENGTH = 2
    }
}