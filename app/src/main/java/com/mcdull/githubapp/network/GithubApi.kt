package com.mcdull.githubapp.network

import OAuthResponse
import com.mcdull.githubapp.model.GitHubSearchResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/repositories")
    suspend fun searchRecentJavaRepositories(
        @Query("q") queries: List<String>,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): GitHubSearchResponse


}

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