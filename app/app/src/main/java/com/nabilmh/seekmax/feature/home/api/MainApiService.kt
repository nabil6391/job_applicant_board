package com.nabilmh.seekmax.feature.home.api

import com.nabilmh.seekmax.models.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainApiService {
    @GET("user/info")
    suspend fun getUserInfo(): Response<UserInfoResponse>
}