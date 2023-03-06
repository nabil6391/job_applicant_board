package com.nabilmh.seekmax.shared

import com.apollographql.apollo.ApolloClient
import com.nabilmh.seekmax.feature.auth.api.AuthApiService
import com.nabilmh.seekmax.feature.auth.repository.AuthRepository
import com.nabilmh.seekmax.feature.home.api.MainApiService
import com.nabilmh.seekmax.feature.home.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun provideAuthRepository(authApiService: AuthApiService) = AuthRepository(authApiService)

    @Provides
    fun provideMainRepository(mainApiService: MainApiService, apolliClient: ApolloClient) =
        MainRepository(mainApiService, apolliClient)
}