package com.mcdull.githubapp.network

import com.mcdull.githubapp.model.OAuthResponse
import com.google.gson.annotations.SerializedName
import com.mcdull.githubapp.model.ContentResponse
import com.mcdull.githubapp.model.GitHubSearchResponse
import com.mcdull.githubapp.model.IssueResponse
import com.mcdull.githubapp.model.Repository
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") queries: List<String>,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): GitHubSearchResponse


    @GET("user/repos")
    suspend fun getUserRepositories(
        @Header("Authorization") token: String,
        @Query("type") type: String = "all",
        @Query("sort") sort: String = "updated",
        @Query("direction") direction: String = "desc"
    ): Response<List<Repository>>

    @GET("repos/{owner}/{repo}/contents/{path}")
    suspend fun getRepoContent(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String
    ): Response<List<ContentResponse>>

    @POST("repos/{owner}/{repo}/issues")
    @Headers(
        "Accept: application/vnd.github+json",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    suspend fun raiseIssue(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body issueRequest: IssueRequest
    ): Response<IssueResponse>
}

data class IssueRequest(
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
    @SerializedName("assignees") val assignees: List<String>? = null,
    @SerializedName("milestone") val milestone: Int? = null,
    @SerializedName("labels") val labels: List<String>? = null
)

interface OAuthApi {

    @FormUrlEncoded
    @POST("login/oauth/access_token")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String,
    ): Response<OAuthResponse>

}