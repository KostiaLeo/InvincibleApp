package com.lyft.android.interviewapp.data.repository

import com.lyft.android.interviewapp.data.local.source.LocalDataSource
import com.lyft.android.interviewapp.data.remote.source.RemoteDataSource
import com.lyft.android.interviewapp.utils.DispatcherProvider
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dispatcherProvider: DispatcherProvider
): Repository {
}