package com.lyft.android.interviewapp.data.repository

import com.google.gson.Gson
import com.lyft.android.interviewapp.data.remote.api.VolunteerIdentityApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class IdentityRepository @Inject constructor(
    private val identityApi: VolunteerIdentityApi
) {
    suspend fun isNewUser(): Boolean {
        return identityApi.login(LoginRequest().toRequestBody()).isNewUser
    }

    suspend fun createAccount(name: String, cityCode: Int) {
        return identityApi.createAccount(CreateAccountRequest(name, cityCode).toRequestBody())
    }
}

data class LoginRequest(val isOrganizer: Boolean = false)
data class CreateAccountRequest(val name: String, val cityCode: Int)

fun <T> T.toRequestBody(): RequestBody {
    val json = Gson().toJson(this)
    return json.toRequestBody(contentType = "application/json".toMediaTypeOrNull())
}