package com.mcdull.githubapp.user

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mcdull.githubapp.data.AuthRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserManager @Inject constructor(
    private val prefs: SharedPreferences
) {
    companion object {
        private const val TAG = "UserManager"
        private const val TOKEN_KEY = "github_access_token"
    }

    @Inject
    lateinit var authRepository: AuthRepository

    private val _accessToken = MutableLiveData<String?>().apply {
        value = prefs.getString(TOKEN_KEY, null)
    }
    val accessToken: LiveData<String?> = _accessToken
//
//    fun isLoggedIn() {
//        return accessToken.value!=null
//    }

    fun saveToken(token: String) {
        prefs.edit().apply {
            putString(TOKEN_KEY, token)
            apply()
        }
        _accessToken.value = token
    }

    fun clearAuth() {
        prefs.edit().apply {
            remove(TOKEN_KEY)
            apply()
        }
        _accessToken.value = null
    }


//    fun handleAuthCallback(code: String) {
//        GlobalScope.launch {
//            try {
//                val token = authRepository.exchangeCodeForToken(code)
//                Log.i(TAG, "handleAuthCallback: token = ${token}")
//                saveToken(token!!.accessToken)
////                _authState.value = AuthState.Success(token!!.accessToken)
//            } catch (e: Exception) {
//                Log.e(TAG, "handleAuthCallback: error", e)
////                _authState.value = AuthState.Error("令牌交换失败: ${e.message}")
//            }
//        }
//    }
}