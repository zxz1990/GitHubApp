package com.mcdull.githubapp.model

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repository(
    val id: Long,
    val name: String,
    val full_name: String,
    val description: String?,
    val html_url: String,
    val stargazers_count: Int,
    val forks_count: Int,
    val language: String?,
    val updated_at: String,
    val owner: Owner,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("open_issues_count") val open_issues_count: Int,
    @SerializedName("archived") val archived: Boolean,
    @SerializedName("license") val license: License?
) : Parcelable {
    val stars get() = stargazers_count
    val forks get() = forks_count
    val updatedAt get() = updated_at
}

@Parcelize
data class Owner(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("type") val type: String
) : Parcelable

@Parcelize
data class License(
    @SerializedName("name") val name: String
) : Parcelable