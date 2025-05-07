package com.mcdull.githubapp.model

import android.util.Base64
import com.google.gson.annotations.SerializedName

data class ContentResponse(
    @SerializedName("name") val name: String,
    @SerializedName("path") val path: String,
    @SerializedName("sha") val sha: String,
    @SerializedName("size") val size: Int,
    @SerializedName("url") val url: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("git_url") val gitUrl: String,
    @SerializedName("download_url") val downloadUrl: String?,
    @SerializedName("type") val type: String,
    @SerializedName("content") val content: String,
    @SerializedName("encoding") val encoding: String
) {
    val decodedContent: String?
        get() = if (encoding == "base64") {
            String(Base64.decode(content, Base64.DEFAULT))
        } else {
            content
        }
}