package com.lyft.android.interviewapp.utils

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun <T> T.toRequestBody(): RequestBody {
    val json = Gson().toJson(this)
    return json.toRequestBody(contentType = "application/json".toMediaTypeOrNull())
}