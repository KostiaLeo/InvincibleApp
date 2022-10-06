package com.lyft.android.interviewapp.ui.screen

import com.lyft.android.interviewapp.utils.UiText

data class UiState(
    val isLoading: Boolean = false,
    val userMessage: UiText? = null
)
