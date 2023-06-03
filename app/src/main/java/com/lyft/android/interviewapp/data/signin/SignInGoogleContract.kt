package com.lyft.android.interviewapp.data.signin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.lyft.android.interviewapp.R

object SignInGoogleContract : ActivityResultContract<Int, Task<GoogleSignInAccount>?>() {
    override fun createIntent(context: Context, input: Int): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.volunteer_server_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        Log.d("LOGIN_FLOW", "gso: $gso")

        val intent = GoogleSignIn.getClient(context, gso)
        return intent.signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Task<GoogleSignInAccount>? {
        Log.d("LOGIN_FLOW", "result: $resultCode, intent: $intent")
        return when (resultCode) {
            Activity.RESULT_OK -> {
                GoogleSignIn.getSignedInAccountFromIntent(intent).also {
                    Log.d("LOGIN_FLOW", "result task: $it")
                }
            }

            else -> null
        }
    }
}