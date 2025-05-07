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
    val updated_at: String
) : Parcelable {
    val stars get() = stargazers_count
    val forks get() = forks_count
    val updatedAt get() = updated_at
}