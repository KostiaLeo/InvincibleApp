package com.lyft.android.interviewapp.di

import com.lyft.android.interviewapp.data.local.source.LocalDataSource
import com.lyft.android.interviewapp.data.local.source.RoomLocalDataSource
import com.lyft.android.interviewapp.data.remote.source.RemoteDataSource
import com.lyft.android.interviewapp.data.remote.source.RetrofitRemoteDataSource
import com.lyft.android.interviewapp.fakesource.FakeLocalDataSource
import com.lyft.android.interviewapp.fakesource.FakeRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataSourceModule::class]
)
interface TestDataSourceModule {

    @Binds
    @Singleton
    fun bindRemoteDataSource(fakeRemoteDataSource: FakeRemoteDataSource): RemoteDataSource

    @Binds
    @Singleton
    fun bindLocalDataSource(fakeLocalDataSource: FakeLocalDataSource): LocalDataSource
}