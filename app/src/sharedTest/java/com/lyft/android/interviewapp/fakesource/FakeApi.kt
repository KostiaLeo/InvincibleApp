package com.lyft.android.interviewapp.fakesource

import com.lyft.android.interviewapp.data.remote.api.MainApi
import com.lyft.android.interviewapp.data.remote.models.MainApiModel

class FakeApi : MainApi {
    override suspend fun getMainModel(): MainApiModel {
        return MainApiModel(0)
    }
}