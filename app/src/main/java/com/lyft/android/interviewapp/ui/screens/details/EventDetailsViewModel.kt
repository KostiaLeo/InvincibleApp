package com.lyft.android.interviewapp.ui.screens.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.lyft.android.interviewapp.data.repository.VolunteerEventsRepository
import com.lyft.android.interviewapp.data.repository.models.EventDetailsUiModel
import com.lyft.android.interviewapp.ui.navigation.NavArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: VolunteerEventsRepository
) : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(PlaceDetailsUiState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val eventId: String by lazy { savedStateHandle[NavArguments.eventId]!! }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    init {
        refresh()
    }

    fun refresh() {
        val storageRef = Firebase.storage.reference
        viewModelScope.launch(exceptionHandler) {
            _uiStateFlow.update { it.copy(isLoading = true) }
            val photos = async {
                storageRef.child(eventId).listAll().await()
                    .items.map { async { it.downloadUrl.await() } }
                    .awaitAll()
                    .map { it.toString() }
            }
            val eventDetails = repository.getEventDetails(eventId)
            Log.d("EVENT_DETAILS", eventDetails.toString())
            _uiStateFlow.update {
                it.copy(
                    isLoading = false,
                    details = eventDetails.copy(photos = photos.await())
                )
            }
        }
    }

    fun registerToEvent() {
        viewModelScope.launch(exceptionHandler) {
            _uiStateFlow.update { it.copy(isLoading = true) }
            repository.registerForEvent(eventId)
            refresh()
        }
    }

    private fun handleError(throwable: Throwable) {
        Log.e("ERROR_EVENT_DETAILS", throwable.localizedMessage, throwable)
        _uiStateFlow.update {
            it.copy(
                isLoading = false,
                errorMessage = throwable.localizedMessage
            )
        }
    }
}

data class PlaceDetailsUiState(
    val details: EventDetailsUiModel = EventDetailsUiModel(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val showConfirmedMessage: Boolean = false
)