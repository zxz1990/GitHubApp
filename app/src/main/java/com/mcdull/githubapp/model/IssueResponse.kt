package com.mcdull.githubapp.model

import com.google.gson.annotations.SerializedName

data class IssueResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("url") val url: String,
    @SerializedName("number") val number: Int,
    @SerializedName("state") val state: String,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String?,
//    @SerializedName("user") val user: User,
    @SerializedName("labels") val labels: List<Label>,
//    @SerializedName("assignees") val assignees: List<User>,
    @SerializedName("milestone") val milestone: Milestone?,
    @SerializedName("comments") val comments: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class Label(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("color") val color: String
)

data class Milestone(
    @SerializedName("id") val id: Long,
    @SerializedName("number") val number: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?
)

data class PullRequest(
    @SerializedName("url") val url: String,
    @SerializedName("html_url") val htmlUrl: String?
)