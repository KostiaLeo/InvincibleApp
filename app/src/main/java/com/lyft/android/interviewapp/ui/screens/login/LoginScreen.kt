package com.lyft.android.interviewapp.ui.screens.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyft.android.interviewapp.data.signin.SignInGoogleContract

private const val googleSignInRequestCode = 1

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginCompleted: () -> Unit
) {
    val authResultLauncher = rememberLauncherForActivityResult(
        contract = SignInGoogleContract,
        onResult = viewModel::handleGoogleAccountTask
    )

    val state by viewModel.isLoginCompleted.collectAsStateWithLifecycle()
    if (state) {
        LaunchedEffect(Unit) {
            onLoginCompleted()
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = {
            authResultLauncher.launch(googleSignInRequestCode)
        }
    ) {
        Text(text = "Sign in with Google")
    }
}