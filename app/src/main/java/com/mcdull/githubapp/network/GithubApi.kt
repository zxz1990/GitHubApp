package com.mcdull.githubapp.network

import com.mcdull.githubapp.model.GitHubSearchResponse
import retrofit2.http.GET
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