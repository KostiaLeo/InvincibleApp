package com.lyft.android.interviewapp.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.inject.Inject
import kotlin.concurrent.read
import kotlin.concurrent.write

class TokenRefreshInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider
) : Interceptor {

    private val readWriteLock = ReentrantReadWriteLock()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val accessToken = readWriteLock.read { tokenProvider.getAccessToken() }
        val request = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        Log.d("TokenRefreshInterceptor", "Bearer $accessToken")

        val response = chain.proceed(request)

        if (response.code == 401) {
            readWriteLock.write {
                // Double-check if the token has been updated by another thread
                if (tokenProvider.getAccessToken() == accessToken) {
                    tokenProvider.refreshAccessToken()
                }
            }

            // Retry the request with the new access token
            val updatedAccessToken = readWriteLock.read { tokenProvider.getAccessToken() }
            val updatedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $updatedAccessToken")
                .build()

            return chain.proceed(updatedRequest)
        }

        return response
    }
}