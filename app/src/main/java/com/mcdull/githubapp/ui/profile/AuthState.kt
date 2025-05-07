package com.mcdull.githubapp.ui.profile

sealed class AuthState {
    object Loading : AuthState()
    data class Success(val token: String) : AuthState()
    data class Error(val message: String) : AuthState()
}