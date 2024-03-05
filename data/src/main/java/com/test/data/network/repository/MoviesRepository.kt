package com.test.data.network.repository

import com.test.data.network.model.PopularMoviesResponse
import retrofit2.Response

interface MoviesRepository {

    suspend fun getPopularMovies(apiKey: String): Response<PopularMoviesResponse>
}