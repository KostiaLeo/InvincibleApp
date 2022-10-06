package com.lyft.android.interviewapp.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource

@Immutable
sealed class UiText {
    @Immutable
    data class StringObject(val value: String) : UiText()
    @Immutable
    data class StringResource(@StringRes val resId: Int) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is StringObject -> value
            is StringResource -> stringResource(resId)
        }
    }
}
