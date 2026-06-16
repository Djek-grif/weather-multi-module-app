package com.djekgrif.weather.core.domain.util

/** Generic typed result usable across all layers (data, domain, presentation, validation). */
sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.djekgrif.weather.core.domain.util.Error>(
        val error: E,
    ) : Result<Nothing, E>
}

typealias EmptyResult<E> = Result<Unit, E>