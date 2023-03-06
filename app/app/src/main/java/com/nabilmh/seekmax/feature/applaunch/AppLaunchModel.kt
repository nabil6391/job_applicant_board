package com.nabilmh.seekmax.feature.applaunch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nabilmh.seekmax.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppLaunchModel @Inject constructor(val tokenManager: TokenManager) : ViewModel() {

    var liveData = MutableLiveData<ViewState>()

    fun init() {
        tokenManager.getToken()?.let {
            liveData.value = ViewState.NavigateToHome
        } ?: run {
            liveData.value = ViewState.NavigateToLogin
        }
    }

    sealed class ViewState {
        object NavigateToLogin : ViewState()
        object NavigateToHome : ViewState()
    }
}
