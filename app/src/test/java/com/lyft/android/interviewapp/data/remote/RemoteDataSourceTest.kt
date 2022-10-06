@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lyft.android.interviewapp.data.remote

import com.lyft.android.interviewapp.data.remote.source.RemoteDataSource
import com.lyft.android.interviewapp.data.remote.source.RetrofitRemoteDataSource
import com.lyft.android.interviewapp.fakesource.FakeApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoteDataSourceTest {
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        remoteDataSource = RetrofitRemoteDataSource(FakeApi())
    }

    @Test
    fun `test remote data source`() = runTest {

    }
}