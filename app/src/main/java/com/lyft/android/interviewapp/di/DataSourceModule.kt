package com.lyft.android.interviewapp.di

import com.lyft.android.interviewapp.data.local.source.LocalDataSource
import com.lyft.android.interviewapp.data.local.source.RoomLocalDataSource
import com.lyft.android.interviewapp.data.remote.source.RemoteDataSource
import com.lyft.android.interviewapp.data.remote.source.RetrofitRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    @Singleton
    fun bindRemoteDataSource(retrofitDataSource: RetrofitRemoteDataSource): RemoteDataSource

    @Binds
    @Singleton
    fun bindLocalDataSource(roomMoviesLocalDataSource: RoomLocalDataSource): LocalDataSource

}