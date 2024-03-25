package com.test.data.network.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.test.data.network.MoviesService
import com.test.data.network.model.Movie
import com.test.data.network.model.MovieVideoInfoResponse
import com.test.data.network.model.PopularMoviesResponse
import retrofit2.Response
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val api: MoviesService
): MoviesRepository {

    override suspend fun getPopularMovies(apiKey: String): Response<PopularMoviesResponse> {
        return api.getPopularMovies(apiKey)
    }

    override fun getPagedPopularMovies(apiKey: String): LiveData<PagingData<Movie>> {
        return Pager(
            PagingConfig(
                pageSize = 20,
                initialLoadSize = 20
            )
        ) {
            MovieListPagingSource(api)
        }.liveData
    }

    override suspend fun searchMovies(searchQuery: String): Response<PopularMoviesResponse> {
        return api.searchMovies(searchQuery = searchQuery)
    }

    override suspend fun getMovieVideoInfo(movieId: String): Response<MovieVideoInfoResponse> {
        return api.getMovieVideoInfo(movieId)
    }
}