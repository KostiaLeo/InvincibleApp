package com.lyft.android.interviewapp.ui.screens.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lyft.android.interviewapp.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    private val _uiStateFlow = MutableStateFlow(SearchUiState("Hello Даня!"))
    val uiStateLiveData: LiveData<SearchUiState> = _uiStateFlow.asLiveData()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ERROR", throwable.localizedMessage)
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            val weather = repository.getWeather().forecasts.joinToString(separator = "\n")
            _uiStateFlow.update { it.copy(data = weather) }
        }

        viewModelScope.launch(exceptionHandler) {
            repository.createUser("Ruslan", 2020, false)
            val users = repository.getAllUsers()
            Log.d("USERZ", users.toString())
        }
    }
}

data class SearchUiState(
    val data: String
)