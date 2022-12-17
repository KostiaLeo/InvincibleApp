package com.lyft.android.interviewapp.di

import com.lyft.android.interviewapp.data.remote.api.VolunteerEventsApi
import com.lyft.android.interviewapp.fakesource.FakeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object TestNetworkModule {

    @Provides
    @Singleton
    fun provideApi(): VolunteerEventsApi {
        return FakeApi()
    }
}