package com.djekgrif.weather.core.presentation.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/** Wraps a user-facing string that may originate from a string resource (and thus be localized). */
sealed interface UiText {
    data class DynamicString(val value: String) : UiText

    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = emptyArray(),
    ) : UiText

    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> value
        is StringResource -> stringResource(id, *args)
    }

    fun asString(context: Context): String = when (this) {
        is DynamicString -> value
        is StringResource -> context.getString(id, *args)
    }
}