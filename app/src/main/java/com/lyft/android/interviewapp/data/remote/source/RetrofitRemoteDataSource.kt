package com.lyft.android.interviewapp.data.remote.source

import com.lyft.android.interviewapp.data.remote.api.MainApi
import javax.inject.Inject

class RetrofitRemoteDataSource @Inject constructor(
    private val mainApi: MainApi
): RemoteDataSource {

}