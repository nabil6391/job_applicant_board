package com.nabilmh.seekmax.shared

import com.apollographql.apollo.ApolloClient
import com.nabilmh.seekmax.BuildConfig
import com.nabilmh.seekmax.feature.auth.api.AuthApiService
import com.nabilmh.seekmax.utils.AuthInterceptor
import com.nabilmh.seekmax.utils.SharedPreferenceManager
import com.nabilmh.seekmax.utils.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Singleton
    @Provides
    fun provideTokenManager(sharedPreferenceManager: SharedPreferenceManager): TokenManager =
        TokenManager(sharedPreferenceManager)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor = AuthInterceptor(tokenManager)

    @Singleton
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.REST_API)
            .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun providerGraphqlClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(BuildConfig.GRAPH_API)
            .okHttpClient(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthAPIService(retrofit: Retrofit.Builder): AuthApiService =
        retrofit
            .build()
            .create(AuthApiService::class.java)


}