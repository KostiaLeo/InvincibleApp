package com.lyft.android.interviewapp.ui.screens.home.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyft.android.interviewapp.data.repository.VolunteerEventsRepository
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel
import com.lyft.android.interviewapp.ui.screens.onboarding.Cities
import com.lyft.android.interviewapp.ui.screens.onboarding.City
import com.lyft.android.interviewapp.utils.EventFilter
import com.lyft.android.interviewapp.utils.defaultFilters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: VolunteerEventsRepository,
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
            val events = repository.getAllEvents(
                uiStateFlow.value.availableFilters.firstOrNull { it.isSelected },
                uiStateFlow.value.selectedCity
            )
            _uiStateFlow.update { it.copy(isLoading = false, events = events) }
        }
    }

    fun onFilterSelected(eventFilter: EventFilter) {
        val availableFilters = if (eventFilter.isSelected) {
            uiStateFlow.value.allFilters
        } else {
            listOf(eventFilter.copy(isSelected = true))
        }
        _uiStateFlow.update { it.copy(availableFilters = availableFilters) }
        refresh()
    }

    fun onCitySelected(city: City) {
        _uiStateFlow.update { it.copy(selectedCity = city) }
        refresh()
    }

    fun refresh() {
        _uiStateFlow.update { it.copy(isLoading = true) }
        loadEvents()
    }
}

data class SearchUiState(
    val isLoading: Boolean = true,
    val events: List<ShortEventUiModel> = emptyList(),
    val errorMessage: String? = null,
    val allFilters: List<EventFilter> = defaultFilters,
    val availableFilters: List<EventFilter> = allFilters,
    val cities: List<City> = Cities() + City("Всі міста", -1),
    val selectedCity: City? = cities.firstOrNull()

)