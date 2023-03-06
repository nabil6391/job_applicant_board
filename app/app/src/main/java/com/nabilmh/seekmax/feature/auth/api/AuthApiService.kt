package com.nabilmh.seekmax.feature.auth.api

import com.nabilmh.seekmax.feature.auth.model.Auth
import com.nabilmh.seekmax.feature.auth.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth")
    suspend fun login(@Body auth: Auth): Response<LoginResponse>

}