@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lyft.android.interviewapp.data

import com.lyft.android.interviewapp.data.repository.DefaultRepository
import com.lyft.android.interviewapp.data.repository.Repository
import com.lyft.android.interviewapp.di.TestDispatcherProvider
import com.lyft.android.interviewapp.fakesource.FakeLocalDataSource
import com.lyft.android.interviewapp.fakesource.FakeRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RepositoryTest {
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        repository = DefaultRepository(
            FakeRemoteDataSource(),
            FakeLocalDataSource(),
            TestDispatcherProvider()
        )
    }

    @Test
    fun `test repository`() = runTest {

    }
}