package com.lyft.android.interviewapp.data.repository

import android.content.SharedPreferences
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.lyft.android.interviewapp.data.remote.api.VolunteerIdentityApi
import com.lyft.android.interviewapp.data.remote.models.MyInfoResponse
import com.lyft.android.interviewapp.di.idToken
import com.lyft.android.interviewapp.di.isSignedIn
import com.lyft.android.interviewapp.utils.toRequestBody
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class IdentityRepository @Inject constructor(
    private val identityApi: VolunteerIdentityApi,
    private val sharedPreferences: SharedPreferences,
    private val googleSignInClient: GoogleSignInClient
) {
    suspend fun isNewUser(): Boolean {
        return identityApi.login(LoginRequest().toRequestBody()).isNewUser
    }

    suspend fun createAccount(name: String, cityCode: Int) {
        return identityApi.createAccount(CreateAccountRequest(name, cityCode).toRequestBody())
    }

    suspend fun getMyInfo(): MyInfoResponse {
//        return MyInfoResponse(
//            name = "John Doe",
//            cityCode = 1,
//            experience = 231,
//            currentPuzzleId = 0,
//            currentPuzzlePieces = listOf(0),
//            slotsAmount = 2,
//            completedPuzzlesIds = listOf(1),
//            level = 2,
//            statistics = listOf(
//                Stat("Закритих сесій", "8"),
//                Stat("Годин витрачено", "∞"),
//                Stat("Дипломних здано", "0"),
//                Stat("Курсових здано", "7")
//            )
//        )
        return identityApi.getMyInfo()
    }

    suspend fun logout() {
        sharedPreferences.isSignedIn = false
        sharedPreferences.idToken = null
        googleSignInClient.signOut().await()
    }
}

data class LoginRequest(val isOrganizer: Boolean = false)
data class CreateAccountRequest(val name: String, val cityCode: Int)