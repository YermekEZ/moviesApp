package com.test.data.network

import com.test.data.network.model.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<PopularMoviesResponse>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = "5b81ffe30455492a85958994a1275dd1",
        @Query("query") searchQuery: String
    ): Response<PopularMoviesResponse>
}