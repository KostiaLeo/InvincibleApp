package com.lyft.android.interviewapp.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.lyft.android.interviewapp.utils.await
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _isLoginCompleted = MutableStateFlow(false)
    val isLoginCompleted = _isLoginCompleted.asStateFlow()

    fun handleGoogleAccountTask(task: Task<GoogleSignInAccount>?) {
        viewModelScope.launch {
            if (task == null) {
                // show error
                return@launch
            }
            val account = task.await()
            val idToken = account.idToken
            val email = account.email
            val name = account.displayName
            val photoUrl = account.photoUrl
            _isLoginCompleted.update { true }
            Log.d("SIIGNNINNGOOGLLEEE", "idToken: $idToken, email: $email, name: $name, photo: $photoUrl")
        }
    }
}