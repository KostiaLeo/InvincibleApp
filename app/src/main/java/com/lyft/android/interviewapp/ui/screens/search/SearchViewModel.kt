package com.lyft.android.interviewapp.ui.screens.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lyft.android.interviewapp.data.repository.VolunteerEventsRepository
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: VolunteerEventsRepository
): ViewModel() {
    private val _uiStateFlow = MutableStateFlow(SearchUiState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ERROR_ALL_EVENTS", throwable.localizedMessage, throwable)
        _uiStateFlow.update { it.copy(isLoading = false, errorMessage = throwable.localizedMessage) }
    }

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch(exceptionHandler) {
            val events = repository.getAllEvents()
            _uiStateFlow.update { it.copy(isLoading = false, events = events) }
        }
    }
}

data class SearchUiState(
    val isLoading: Boolean = true,
    val events: List<ShortEventUiModel> = emptyList(),
    val errorMessage: String? = null
)