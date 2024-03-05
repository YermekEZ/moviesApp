package com.test.domain.useCases

import com.test.data.network.model.PopularMoviesResponse
import com.test.data.network.repository.MoviesRepository
import com.test.domain.useCases.model.PopularMovieModel
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend fun getPopularMovies(apiKey: String): List<PopularMovieModel> {
        val result = repository.getPopularMovies(apiKey)
        if (result.isSuccessful) {
            return transformToPopularMovieModel(result.body())
        } else {
            return emptyList()
        }
    }

    private fun transformToPopularMovieModel(data: PopularMoviesResponse?): List<PopularMovieModel> {
        val popularMoviesList = mutableListOf<PopularMovieModel>().apply {
            data?.results?.let {
                it.forEach { responseData ->
                    add(
                        PopularMovieModel(
                            adult = responseData.adult,
                            title = responseData.title ?: "The movie does not have a title",
                            voteAverage = responseData.voteAverage ?: 0.0,
                            posterImage = responseData.posterPath
                        )
                    )
                }
            }
        }
        return popularMoviesList
    }
}