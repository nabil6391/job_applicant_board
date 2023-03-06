package com.nabilmh.seekmax

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.nabilmh.seekmax.feature.auth.ui.LoginActivity
import com.nabilmh.seekmax.feature.home.ui.MainActivity
import com.nabilmh.seekmax.shared.AppLaunchModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppLaunchActivity : ComponentActivity() {

    private val viewModel: AppLaunchModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init()

        viewModel.liveData.observe(this) {
            when (it) {
                is AppLaunchModel.ViewState.NavigateToLogin -> {
                    // Navigate to login
                    startActivity(LoginActivity.newIntent(this))
                }
                is AppLaunchModel.ViewState.NavigateToHome -> {
                    // Navigate to home
                    startActivity(MainActivity.newIntent(this))
                }
            }
        }

    }
}
