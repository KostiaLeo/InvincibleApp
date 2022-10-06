package com.lyft.android.interviewapp.di

import com.lyft.android.interviewapp.utils.DefaultDispatcherProvider
import com.lyft.android.interviewapp.utils.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {

    @Binds
    @Singleton
    fun bindDispatcherProvider(defaultDispatcherProvider: DefaultDispatcherProvider): DispatcherProvider
}