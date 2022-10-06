package com.lyft.android.interviewapp.di

import com.lyft.android.interviewapp.data.remote.api.MainApi
import com.lyft.android.interviewapp.fakesource.FakeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object TestNetworkModule {

    @Provides
    @Singleton
    fun provideApi(): MainApi {
        return FakeApi()
    }
}