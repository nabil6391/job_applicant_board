package com.nabilmh.seekmax.feature.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nabilmh.seekmax.feature.auth.repository.AuthRepository
import com.nabilmh.seekmax.utils.ApiResponse
import com.nabilmh.seekmax.utils.TokenManager

import com.nabilmh.seekmax.viewmodels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) : BaseViewModel() {

    var viewStateLiveData = MutableLiveData<ViewResult>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            viewStateLiveData.value = ViewResult.Loading

            val response = authRepository.login(email, password)

            when (response) {
                is ApiResponse.Success -> {
                    tokenManager.saveToken(response.data.token)
                    viewStateLiveData.value = ViewResult.Success
                }
                is ApiResponse.Failure -> {
                    viewStateLiveData.value = ViewResult.Error(response.errorMessage)

                }
                ApiResponse.Loading -> {

                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenManager.deleteToken()
        }
    }

    sealed class ViewResult {
        object Loading : ViewResult()
        object Success : ViewResult()
        data class Error(val errorMessage: String) : ViewResult()
    }

}