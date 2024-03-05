package com.test.data.network.repository

import com.test.data.network.MoviesService
import com.test.data.network.model.PopularMoviesResponse
import retrofit2.Response
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val api: MoviesService
): MoviesRepository {

    override suspend fun getPopularMovies(apiKey: String): Response<PopularMoviesResponse> {
        return api.getPopularMovies(apiKey)
    }
}