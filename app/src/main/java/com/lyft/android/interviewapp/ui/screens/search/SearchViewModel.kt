package com.lyft.android.interviewapp.ui.screens.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lyft.android.interviewapp.data.repository.VolunteerEventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: VolunteerEventsRepository
): ViewModel() {
    private val _uiStateFlow = MutableStateFlow(SearchUiState("Hello Даня!"))
    val uiStateLiveData: LiveData<SearchUiState> = _uiStateFlow.asLiveData()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ERROR", throwable.localizedMessage)
    }

    init {

    }
}

data class SearchUiState(
    val data: String
)