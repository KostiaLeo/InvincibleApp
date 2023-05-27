package com.lyft.android.interviewapp.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyft.android.interviewapp.data.repository.VolunteerEventsRepository
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel
import com.lyft.android.interviewapp.ui.screens.onboarding.Cities
import com.lyft.android.interviewapp.ui.screens.onboarding.City
import com.lyft.android.interviewapp.ui.screens.search.content.EventFilter
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
) : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(SearchUiState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ERROR_ALL_EVENTS", throwable.localizedMessage, throwable)
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
            val events = repository.getAllEvents()
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

    fun onCitySelected(city: City) {
        viewModelScope.launch {
            _uiStateFlow.update { it.copy(selectedCity = city) }
            // TODO: load filtered events
        }
    }
}

data class SearchUiState(
    val isLoading: Boolean = true,
    val events: List<ShortEventUiModel> = emptyList(),
    val errorMessage: String? = null,
    val allFilters: List<EventFilter> = defaultFilters,
    val availableFilters: List<EventFilter> = allFilters,
    val cities: List<City> = Cities(),
    val selectedCity: City? = cities.firstOrNull()

)

private val defaultFilters by lazy {
    listOf(
        EventFilter("Цього тижня", false, 0),
        EventFilter("Цього місяця", false, 1),
        EventFilter("1-2 год", false, 2),
        EventFilter("3-4 год", false, 3),
    )
}