package com.nabilmh.seekmax.utils

class TokenManager(private val sharedPreferenceManager: SharedPreferenceManager) {

    fun getToken(): String? {
        return sharedPreferenceManager.token
    }

    fun saveToken(token: String) {
        sharedPreferenceManager.token = token
    }

    fun deleteToken() {
        sharedPreferenceManager.token = null
    }
}