package com.lyft.android.interviewapp.ui.screens.event_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lyft.android.interviewapp.data.repository.VolunteerEventsRepository
import com.lyft.android.interviewapp.data.repository.models.EventDetailsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: VolunteerEventsRepository
) : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(PlaceDetailsUiState())
    val uiStateLiveData = _uiStateFlow.asLiveData()

    private val args = EventDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    init {
        loadEvent()
    }

    private fun loadEvent() {
        viewModelScope.launch(exceptionHandler) {
            val eventDetails = repository.getEventDetails(args.eventId)
            _uiStateFlow.update { it.copy(isLoading = false, details = eventDetails) }
        }
    }

    fun registerToEvent() {
        viewModelScope.launch(exceptionHandler) {
            _uiStateFlow.update { it.copy(isLoading = true) }
            val registerResponse = repository.registerForEvent(args.eventId)
            _uiStateFlow.update {
                it.copy(
                    isLoading = false,
                    details = it.details.copy(
                        isRegistered = true,
                        volunteersCount = it.details.volunteersCount
                            .replaceBefore('/', registerResponse.newCurVolunteersCount.toString()) // very bad but idc ;)
                    )
                )
            }
        }
    }

    private fun handleError(throwable: Throwable) {
        Log.e("ERROR_EVENT_DETAILS", throwable.localizedMessage, throwable)
        _uiStateFlow.update { it.copy(isLoading = false, errorMessage = throwable.localizedMessage) }
    }

    fun onErrorMessageShown() {
        _uiStateFlow.update { it.copy(errorMessage = null) }
    }
}

data class PlaceDetailsUiState(
    val isLoading: Boolean = true,
    val details: EventDetailsUiModel = EventDetailsUiModel(),
    val errorMessage: String? = null
)