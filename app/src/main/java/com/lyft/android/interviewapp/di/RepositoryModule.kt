package com.lyft.android.interviewapp.di

import com.lyft.android.interviewapp.data.repository.DefaultRepository
import com.lyft.android.interviewapp.data.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindMoviesRepository(defaultRepository: DefaultRepository): Repository
}