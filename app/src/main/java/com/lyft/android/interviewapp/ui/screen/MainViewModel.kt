package com.lyft.android.interviewapp.ui.screen

import androidx.lifecycle.ViewModel
import com.lyft.android.interviewapp.domain.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(UiState())
    val uiStateFlow = _uiStateFlow.asStateFlow()



    fun onUserMessageShown() {
        _uiStateFlow.update {
            it.copy(userMessage = null)
        }
    }
}