package com.lyft.android.interviewapp.ui.screens.home.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    var uiState: ProfileUiState by mutableStateOf(ProfileUiState())
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
            uiState = uiState.copy(
                isLoading = true
            )
            identityRepository.getMyInfo().let {
                uiState = uiState.copy(
                    isLoading = false,
                    name = it.name
                )
            }
        }
    }

    fun refresh() {
        Log.d("REFRESH", "Refreshing profile")
        loadProfile()
    }

    fun logout() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true
            )
            identityRepository.logout()
            Log.d("LOGOUT", "Logged out")
            uiState = uiState.copy(
                isLoggedOut = true,
                isLoading = false
            )
        }
    }
}

data class ProfileUiState(
    val isLoading: Boolean = true,
    val name: String = "",
    val errorMessage: String? = null,
    val isLoggedOut: Boolean = false
)