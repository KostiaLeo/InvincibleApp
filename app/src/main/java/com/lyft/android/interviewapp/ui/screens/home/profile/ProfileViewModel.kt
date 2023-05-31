package com.lyft.android.interviewapp.ui.screens.home.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyft.android.interviewapp.data.repository.IdentityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val identityRepository: IdentityRepository
) : ViewModel() {
    var uiState: ProfileUiState = ProfileUiState()
        private set

    // exception handler
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable: Throwable ->
        Log.d("ERROR_PROFILE", throwable.message, throwable)
        uiState = uiState.copy(
            isLoading = false,
            errorMessage = throwable.message
        )
    }

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch(exceptionHandler) {
            identityRepository.getMyInfo().let {
                uiState = uiState.copy(
                    isLoading = false,
                    name = it.name
                )
            }
        }
    }
}

data class ProfileUiState(
    val isLoading: Boolean = true,
    val name: String = "",
    val errorMessage: String? = null
)