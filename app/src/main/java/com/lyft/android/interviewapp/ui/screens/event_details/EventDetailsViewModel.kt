package com.lyft.android.interviewapp.ui.screens.event_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lyft.android.interviewapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(PlaceDetailsUiState(""))
    val uiStateLiveData = _uiStateFlow.asLiveData()

    private val args = EventDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        _uiStateFlow.update { it.copy(name = "Руслан ${args.placeId}") }
    }
}

data class PlaceDetailsUiState(
    val name: String,
)