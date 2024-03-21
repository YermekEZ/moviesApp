package com.test.data.network.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.data.network.model.Movie
import com.test.data.network.model.PopularMoviesResponse
import retrofit2.Response

interface MoviesRepository {

    suspend fun getPopularMovies(apiKey: String): Response<PopularMoviesResponse>

    fun getPagedPopularMovies(apiKey: String): LiveData<PagingData<Movie>>

    suspend fun searchMovies(searchQuery: String): Response<PopularMoviesResponse>
}