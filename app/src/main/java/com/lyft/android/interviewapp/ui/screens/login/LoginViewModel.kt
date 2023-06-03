package com.lyft.android.interviewapp.ui.screens.login

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.lyft.android.interviewapp.data.repository.IdentityRepository
import com.lyft.android.interviewapp.di.idToken
import com.lyft.android.interviewapp.di.isSignedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
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

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("LOGIN_FLOW", "ERROR: ${throwable.localizedMessage}", throwable)
        _uiState.update { it.copy(errorMessage = throwable.localizedMessage, showProgress = false) }
    }

    fun handleGoogleAccountTask(task: Task<GoogleSignInAccount>?) {
        Log.d("LOGIN_FLOW", "task: $task")
        viewModelScope.launch(exceptionHandler) {
            if (task == null) {
                // show error
                Log.d("LOGIN_FLOW", "task is null")
                error("Google Task is null")
            }

            _uiState.update { it.copy(showProgress = true) }
            val account = task.await()
            Log.d("LOGIN_FLOW", "account ${account.account}")
            sharedPreferences.idToken = account.idToken
            val isNewUser = identityRepository.isNewUser()

            if (!isNewUser) {
                sharedPreferences.isSignedIn = true
            }

            Log.d("LOGIN_FLOW", "acc: ${account.account}, is new user: $isNewUser")

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

    fun onErrorShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

data class LoginUiState(
    val showProgress: Boolean = false,
    val isLoginCompleted: Boolean = false,
    val isNewUser: Boolean = false,
    val userName: String = "",
    val errorMessage: String? = null
)