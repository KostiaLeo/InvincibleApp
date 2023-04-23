package com.lyft.android.interviewapp.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.lyft.android.interviewapp.utils.await
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
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
            Log.d("SIIGNNINNGOOGLLEEE", "idToken: $idToken, email: $email, name: $name, photo: $photoUrl")
        }
    }
}