@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lyft.android.interviewapp.viewmodel

import com.lyft.android.interviewapp.domain.UseCase
import com.lyft.android.interviewapp.fakesource.FakeRepository
import com.lyft.android.interviewapp.ui.screen.MainViewModel
import com.lyft.android.interviewapp.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFalse

class ViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule(testDispatcher)

    private lateinit var repository: FakeRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        repository = FakeRepository()
        viewModel = MainViewModel(UseCase(repository))
    }

    @Test
    fun `test view model`() = runTest {
        val collectJob = launch(testDispatcher) { viewModel.uiStateFlow.collect() }

        val state = viewModel.uiStateFlow.value
        assertFalse(state.isLoading)

        collectJob.cancel()
    }
}