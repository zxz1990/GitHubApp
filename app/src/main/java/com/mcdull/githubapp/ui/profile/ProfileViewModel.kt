package com.mcdull.githubapp.ui.profile

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcdull.githubapp.data.AuthRepository
import com.mcdull.githubapp.network.OAuthClient
import kotlinx.coroutines.launch


class ProfileViewModel(
) : ViewModel() {
    companion object{
        private const val TAG = "ProfileViewModel"
    }
    
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState
    private val authRepository: AuthRepository by lazy {
        AuthRepository(OAuthClient.apiService, null)

    }


    fun startOAuthFlow(context: Context) {
        viewModelScope.launch {
            try {
                val authUrl = authRepository.buildAuthUrl()
                _authState.value = AuthState.Loading
                // 启动浏览器进行认证
                context.startActivity(Intent(Intent.ACTION_VIEW, authUrl.toUri()))
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "认证初始化失败")
            }
        }
    }

    fun handleAuthCallback(code: String) {
        viewModelScope.launch {
            try {
                val token = authRepository.exchangeCodeForToken(code)
                Log.i(TAG, "handleAuthCallback: token = ${token}")
                _authState.value = AuthState.Success(token!!.accessToken)
            } catch (e: Exception) {
                Log.e(TAG, "handleAuthCallback: error", e)
                _authState.value = AuthState.Error("令牌交换失败: ${e.message}")
            }
        }
    }
}