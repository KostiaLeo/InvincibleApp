package com.lyft.android.interviewapp.data.network

import android.content.SharedPreferences
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.lyft.android.interviewapp.di.idToken
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

interface TokenProvider {
    fun getAccessToken(): String
    fun refreshAccessToken()
}

class GoogleTokenProvider @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val googleSignInClient: GoogleSignInClient
) : TokenProvider {

    override fun getAccessToken(): String {
        return requireNotNull(sharedPreferences.idToken)
    }

    override fun refreshAccessToken() {
        val countDownLatch = CountDownLatch(1)
        googleSignInClient.silentSignIn()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sharedPreferences.idToken = task.result.idToken.also {
                        Log.d("GoogleSignIn", "New token: $it")
                    }
                }
                countDownLatch.countDown()
            }
        countDownLatch.await()
    }
}