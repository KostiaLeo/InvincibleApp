package com.lyft.android.interviewapp.ui.screens.login

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.lyft.android.interviewapp.data.repository.IdentityRepository
import com.lyft.android.interviewapp.di.idToken
import com.lyft.android.interviewapp.di.isSignedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val identityRepository: IdentityRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun handleGoogleAccountTask(task: Task<GoogleSignInAccount>?) {
        viewModelScope.launch {
            if (task == null) {
                // show error
                return@launch
            }

            _uiState.update { it.copy(showProgress = true) }
            val account = task.await()
            sharedPreferences.idToken = account.idToken
            val isNewUser = identityRepository.isNewUser()

            if (!isNewUser) {
                sharedPreferences.isSignedIn = true
            }

            _uiState.update {
                LoginUiState(
                    showProgress = false,
                    isLoginCompleted = true,
                    isNewUser = isNewUser,
                    userName = account.givenName ?: account.displayName ?: ""
                )
            }
        }
    }
}

data class LoginUiState(
    val showProgress: Boolean = false,
    val isLoginCompleted: Boolean = false,
    val isNewUser: Boolean = false,
    val userName: String = ""
)