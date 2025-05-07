package com.mcdull.githubapp.viewmodel

import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    fun startOAuthFlow() {
        // 在此实现具体的 OAuth 认证逻辑
        // 需要调用 GitHub OAuth 接口：
        // https://github.com/login/oauth/authorize?client_id=YOUR_CLIENT_ID
    }
}