package com.djekgrif.weather.core.presentation.mapper

import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.presentation.R
import com.djekgrif.weather.core.presentation.ui.UiText

/** Shared mapping of [DataError] cases to localized, user-facing strings. */
fun DataError.toUiText(): UiText = when (this) {
    DataError.Network.NO_INTERNET -> UiText.StringResource(R.string.error_no_internet)
    DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(R.string.error_request_timeout)
    DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(R.string.error_too_many_requests)
    DataError.Network.SERVER_ERROR,
    DataError.Network.SERVICE_UNAVAILABLE,
    -> UiText.StringResource(R.string.error_server)

    DataError.Network.UNAUTHORIZED,
    DataError.Network.FORBIDDEN,
    -> UiText.StringResource(R.string.error_unauthorized)

    DataError.Network.NOT_FOUND -> UiText.StringResource(R.string.error_not_found)
    DataError.Network.SERIALIZATION -> UiText.StringResource(R.string.error_serialization)
    DataError.Local.DISK_FULL -> UiText.StringResource(R.string.error_disk_full)
    else -> UiText.StringResource(R.string.error_unknown)
}