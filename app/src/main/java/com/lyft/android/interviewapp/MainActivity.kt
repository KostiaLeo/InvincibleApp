package com.lyft.android.interviewapp

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.material.snackbar.Snackbar
import com.lyft.android.interviewapp.di.isSignedIn
import com.lyft.android.interviewapp.ui.navigation.AppNavHost
import com.lyft.android.interviewapp.ui.navigation.Routes
import com.lyft.android.interviewapp.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "LOGIN_PROCESS"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    private var idToken by mutableStateOf<String?>(null)
    private var name by mutableStateOf<String?>(null)

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                name = credential.id
                val password = credential.password
                when {
                    idToken != null -> {
                        this.idToken = idToken
                        // Got an ID token from Google. Use it to authenticate
                        // with your backend.
                        Log.d(TAG, "Got ID token.")
                    }

                    password != null -> {
                        // Got a saved username and password. Use them to authenticate
                        // with your backend.
                        Log.d(TAG, "Got password.")
                    }

                    else -> {
                        // Shouldn't happen.
                        Log.d(TAG, "No ID token or password!")
                    }
                }
            } catch (e: Exception) {
                Snackbar.make(this.window.decorView, "${e.message}", Snackbar.LENGTH_SHORT).show()
                Log.e(TAG, e.localizedMessage, e)
                // ...
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        if (!sharedPreferences.isSignedIn) {
        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.volunteer_server_client_id))

                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            // Automatically sign in when exactly one credential is retrieved.
//                .setAutoSelectEnabled(true)
            .build()
//        }
        setContent {
            AppTheme {
                val startDestination =
                    if (sharedPreferences.isSignedIn) Routes.home else Routes.login
                AppNavHost(
                    startDestination = startDestination,
                    startLogin = {
                        oneTapClient.beginSignIn(signInRequest)
                            .addOnSuccessListener(this) { result ->
                                googleSignInLauncher.launch(
                                    IntentSenderRequest.Builder(result.pendingIntent).build()
                                )
                            }
                            .addOnFailureListener(this) { e ->
                                Snackbar.make(
                                    this.window.decorView,
                                    "${e.message}",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                                Log.d(TAG, e.localizedMessage)
                            }
                    },
                    idToken = idToken,
                    name = name
                )
            }
        }
    }
}