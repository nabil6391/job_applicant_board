package com.nabilmh.seekmax.feature.auth.repository

import com.nabilmh.seekmax.feature.auth.api.AuthApiService
import com.nabilmh.seekmax.feature.auth.model.Auth
import com.nabilmh.seekmax.utils.apiCall
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
) {
    suspend fun login(email: String, password: String) = apiCall { authApiService.login(Auth(email, password)) }
}