package com.test.domain.useCases

import com.test.data.network.model.MovieVideoInfoResponse
import com.test.data.network.model.PopularMoviesResponse
import com.test.data.network.repository.MoviesRepository
import com.test.domain.useCases.model.MovieModel
import com.test.domain.useCases.model.MovieVideoInfoModel
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend fun searchMovies(searchQuery: String): List<MovieModel> {
        val result = repository.searchMovies(searchQuery)
        if (result.isSuccessful) {
            return transformToPopularMovieModel(result.body())
        } else {
            return emptyList()
        }
    }

    private fun transformToPopularMovieModel(data: PopularMoviesResponse?): List<MovieModel> {
        val popularMoviesList = mutableListOf<MovieModel>().apply {
            data?.results?.let {
                it.forEach { responseData ->
                    add(
                        MovieModel(
                            id = responseData.id,
                            title = responseData.title ?: "The movie does not have a title",
                            description = responseData.overview ?: "No description",
                            voteAverage = responseData.voteAverage ?: 0.0,
                            posterImage = responseData.posterPath.orEmpty().takeIf { it.isNotBlank() } ?: "https://i.pinimg.com/564x/50/6f/3c/506f3c5fcb942c54fbe2f5f84b96d2c8.jpg"
                        )
                    )
                }
            }
        }
        return popularMoviesList
    }
}

class GetMovieVideoInfoUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend fun getMovieVideoInfo(movieId: String): List<MovieVideoInfoModel> {
        val result = repository.getMovieVideoInfo(movieId)
        if (result.isSuccessful) {
            return transformToMovieVideoInfoModel(result.body())
        } else {
            return emptyList()
        }
    }

    private fun transformToMovieVideoInfoModel(data: MovieVideoInfoResponse?): List<MovieVideoInfoModel> {
        val movieVideoInfoList = mutableListOf<MovieVideoInfoModel>().apply {
            data?.results?.let {
                it.forEach { videoInfo ->
                    add(
                        MovieVideoInfoModel(
                            name = videoInfo.name.orEmpty(),
                            key = videoInfo.key.orEmpty(),
                            type = videoInfo.type.orEmpty(),
                            official = videoInfo.official ?: false
                        )
                    )
                }
            }
        }
        return movieVideoInfoList
    }
}