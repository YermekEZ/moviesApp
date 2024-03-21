package com.test.domain.useCases

import com.test.data.network.model.PopularMoviesResponse
import com.test.data.network.repository.MoviesRepository
import com.test.domain.useCases.model.MovieModel
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
                            posterImage = responseData.posterPath.orEmpty()
                        )
                    )
                }
            }
        }
        return popularMoviesList
    }
}