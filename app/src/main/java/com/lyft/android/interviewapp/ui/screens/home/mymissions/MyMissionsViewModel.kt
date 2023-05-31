package com.lyft.android.interviewapp.ui.screens.home.mymissions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyft.android.interviewapp.data.repository.VolunteerEventsRepository
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel
import com.lyft.android.interviewapp.utils.EventFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMissionsViewModel @Inject constructor(
    private val repository: VolunteerEventsRepository
) : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(MyMissionsUiState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ERROR_MY_EVENTS", throwable.localizedMessage, throwable)
        _uiStateFlow.update {
            it.copy(
                isLoading = false,
                errorMessage = throwable.localizedMessage
            )
        }
    }

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch(exceptionHandler) {
            val events = repository.getMyMissionsList()
            _uiStateFlow.update { it.copy(isLoading = false, events = events) }
        }
    }

    fun onFilterSelected(eventFilter: EventFilter) {
        viewModelScope.launch {
            val availableFilters = if (eventFilter.isSelected) {
                uiStateFlow.value.allFilters
            } else {
                listOf(eventFilter.copy(isSelected = true))
            }
            _uiStateFlow.update { it.copy(availableFilters = availableFilters) }
            if (!eventFilter.isSelected) {
                // TODO: retrieve filtered events
            }
        }
    }
}

data class MyMissionsUiState(
    val isLoading: Boolean = true,
    val events: List<ShortEventUiModel> = emptyList(),
    val errorMessage: String? = null,
    val allFilters: List<EventFilter> = myMissionsFilters,
    val availableFilters: List<EventFilter> = allFilters
)

private val myMissionsFilters by lazy {
    listOf(
        EventFilter("Активні", false, 0),
        EventFilter("Завершені", false, 1),
    )
}