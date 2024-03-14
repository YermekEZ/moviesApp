package com.test.domain.useCases

import com.test.data.local.model.FavouriteMovieEntity
import com.test.data.local.repository.MoviesLocalRepository
import com.test.domain.useCases.model.PopularMovieModel
import javax.inject.Inject

class AddMovieUseCase @Inject constructor(
    private val repository: MoviesLocalRepository
) {
    suspend fun addMovie(movie: PopularMovieModel) {
        val movieEntity = FavouriteMovieEntity(
            title = movie.title,
            description = movie.description,
            averageRating = movie.voteAverage.toString(),
            imageUrl = movie.posterImage
        )
        repository.addMovie(movieEntity)
    }
}

class GetAllFavouriteMoviesUseCase @Inject constructor(
    private val repository: MoviesLocalRepository
) {
    suspend fun getAllFavouriteMovies(): List<PopularMovieModel> {
        val favouriteMovies = repository.getAllFavouriteMovies()
        return transformFavouriteMovieEntityToMovieModel(favouriteMovies)
    }

    private fun transformFavouriteMovieEntityToMovieModel(movies: List<FavouriteMovieEntity>): List<PopularMovieModel> {
        val favouriteMoviesList = movies.map {
            PopularMovieModel(
                title = it.title,
                description = it.description,
                voteAverage = it.averageRating.toDouble(),
                posterImage = it.imageUrl
            )
        }
        return favouriteMoviesList
    }
}