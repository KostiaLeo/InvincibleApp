@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lyft.android.interviewapp.data

import com.lyft.android.interviewapp.data.repository.RemoteVolunteerEventsRepository
import com.lyft.android.interviewapp.data.repository.VolunteerEventsRepository
import com.lyft.android.interviewapp.di.TestDispatcherProvider
import com.lyft.android.interviewapp.fakesource.FakeLocalDataSource
import com.lyft.android.interviewapp.fakesource.FakeRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class VolunteerEventsRepositoryTest {
    private lateinit var repository: VolunteerEventsRepository

    @Before
    fun setUp() {
        repository = RemoteVolunteerEventsRepository(
            FakeRemoteDataSource(),
            FakeLocalDataSource(),
            TestDispatcherProvider()
        )
    }

    @Test
    fun `test repository`() = runTest {

    }
}