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
import com.mcdull.githubapp.model.Repository
import com.mcdull.githubapp.network.OAuthClient
import com.mcdull.githubapp.network.RetrofitClient
import com.mcdull.githubapp.user.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {
    companion object {
        private const val TAG = "ProfileViewModel"
    }

    // 使用userManager管理用户状态
//    fun checkLoginState(): Boolean {
//        return userManager.isLoggedIn
//    }

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState
    private val authRepository: AuthRepository by lazy {
        AuthRepository(OAuthClient.apiService)

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
                userManager.saveToken(token!!.accessToken)
                _authState.value = AuthState.Success(token!!.accessToken)
            } catch (e: Exception) {
                Log.e(TAG, "handleAuthCallback: error", e)
                _authState.value = AuthState.Error("令牌交换失败: ${e.message}")
            }
        }
    }

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> = _repositories

    fun loadUserRepositories(token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getUserRepositories("Bearer $token")
                if (response.isSuccessful) {
                    _repositories.value = response.body()
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("获取仓库失败: ${e.message}")
            }
        }
    }
}