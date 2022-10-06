@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lyft.android.interviewapp.di

import com.lyft.android.interviewapp.utils.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoroutinesModule::class]
)
interface CoroutinesTestModule {

    @Binds
    @Singleton
    fun bindTestDispatcherProvider(
        testDispatcherProvider: TestDispatcherProvider
    ): DispatcherProvider
}

class TestDispatcherProvider @Inject constructor() : DispatcherProvider {
    override val io: CoroutineDispatcher = UnconfinedTestDispatcher()
    override val default: CoroutineDispatcher = UnconfinedTestDispatcher()
    override val main: CoroutineDispatcher = UnconfinedTestDispatcher()
}