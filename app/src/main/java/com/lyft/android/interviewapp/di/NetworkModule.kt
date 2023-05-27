package com.lyft.android.interviewapp.di

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.lyft.android.interviewapp.BuildConfig
import com.lyft.android.interviewapp.R
import com.lyft.android.interviewapp.data.network.GoogleTokenProvider
import com.lyft.android.interviewapp.data.network.TokenProvider
import com.lyft.android.interviewapp.data.network.TokenRefreshInterceptor
import com.lyft.android.interviewapp.data.remote.api.VolunteerEventsApi
import com.lyft.android.interviewapp.data.remote.api.VolunteerIdentityApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    companion object {
        @Provides
        @Singleton
        fun provideEventsApi(retrofit: Retrofit): VolunteerEventsApi {
            return retrofit.create()
        }

        @Provides
        @Singleton
        fun provideIdentityApi(retrofit: Retrofit): VolunteerIdentityApi {
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
                    callTimeout(1L, TimeUnit.MINUTES)
                    connectTimeout(1L, TimeUnit.MINUTES)
                    readTimeout(1L, TimeUnit.MINUTES)
                    writeTimeout(1L, TimeUnit.MINUTES)
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
        @Singleton
        fun provideGoogleSignInClient(application: Application): GoogleSignInClient {
            val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(application.getString(R.string.volunteer_server_client_id))
                .requestEmail()
                .requestProfile()
                .build()

            return GoogleSignIn.getClient(application, signInOptions)
        }
    }

    @Binds
    @Singleton
    abstract fun bindTokenProvider(googleTokenProvider: GoogleTokenProvider): TokenProvider

    @Binds
    @IntoSet
    @Interceptors
    @Singleton
    abstract fun bindTokenRefreshInterceptor(tokenRefreshInterceptor: TokenRefreshInterceptor): Interceptor
}