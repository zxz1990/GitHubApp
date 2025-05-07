package com.mcdull.githubapp.model

import com.google.gson.annotations.SerializedName

data class GitHubSearchResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val items: List<Repository>
)
