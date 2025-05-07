package com.mcdull.githubapp.data

import OAuthResponse
import com.mcdull.githubapp.network.OAuthApi
import java.net.URLEncoder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: OAuthApi,
) {
    private val clientId = "Iv23li0Zdy5dDrLuJynv"
    private val clientSecret = "add0af59c0ff9fef62b6bbdc8ff49f6a0e9f1925"
    private val redirectUri = "githubapp://oauth-callback"  // 修改为自定义URI Scheme

    fun buildAuthUrl(): String {
        return "https://github.com/login/oauth/authorize" +
            "?client_id=$clientId" +
            "&redirect_uri=${URLEncoder.encode(redirectUri, "UTF-8")}" + 
            "&scope=user,repo"
    }

    suspend fun exchangeCodeForToken(code: String): OAuthResponse? {
        val response = apiService.getAccessToken(
            clientId = clientId,
            clientSecret = clientSecret,
            code = code,
            redirectUri = redirectUri
        )
        return response.body()
    }
}