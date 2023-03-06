package com.nabilmh.seekmax.feature.auth.model

import com.google.gson.annotations.SerializedName

data class Auth(
    @SerializedName("user")
    val email: String,
    val password: String
)