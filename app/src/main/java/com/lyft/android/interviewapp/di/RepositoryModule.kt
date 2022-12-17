package com.lyft.android.interviewapp.di

import com.lyft.android.interviewapp.data.repository.RemoteVolunteerEventsRepository
import com.lyft.android.interviewapp.data.repository.VolunteerEventsRepository
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
    fun bindRepository(defaultRepository: RemoteVolunteerEventsRepository): VolunteerEventsRepository
}