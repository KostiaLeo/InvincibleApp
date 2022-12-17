package com.lyft.android.interviewapp.di

import com.lyft.android.interviewapp.BuildConfig
import com.lyft.android.interviewapp.data.remote.api.MainApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): MainApi {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .baseUrl(BuildConfig.API_BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Interceptors interceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                interceptors.forEach(::addInterceptor)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @IntoSet
    @Interceptors
    @Singleton
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @IntoSet
    @Interceptors
    @Singleton
    fun provideAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "VasyaLoh")
                .build()
            chain.proceed(request)
        }
    }
}