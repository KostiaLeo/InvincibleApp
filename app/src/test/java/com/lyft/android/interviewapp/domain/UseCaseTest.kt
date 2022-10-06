@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lyft.android.interviewapp.domain

import com.lyft.android.interviewapp.fakesource.FakeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UseCaseTest {
    private lateinit var repository: FakeRepository
    private lateinit var useCase: UseCase

    @Before
    fun setUp() {
        repository = FakeRepository()
        useCase = UseCase(repository)
    }

    @Test
    fun `test use case`() = runTest {
        useCase()
    }
}