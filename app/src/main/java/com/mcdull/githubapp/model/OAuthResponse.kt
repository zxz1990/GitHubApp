package com.mcdull.githubapp.model

import com.google.gson.annotations.SerializedName

data class OAuthResponse(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("scope")
    val scope: String?
)