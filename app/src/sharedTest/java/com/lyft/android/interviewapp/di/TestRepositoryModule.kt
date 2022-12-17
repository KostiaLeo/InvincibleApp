package com.lyft.android.interviewapp.di

import com.lyft.android.interviewapp.fakesource.FakeVolunteerEventsRepository
import com.lyft.android.interviewapp.data.repository.VolunteerEventsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
interface TestRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(fakeRepository: FakeVolunteerEventsRepository): VolunteerEventsRepository
}