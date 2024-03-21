package com.test.domain.useCases

import com.test.data.local.model.FavouriteMovieEntity
import com.test.data.local.model.RecentlySearchedMovieEntity
import com.test.data.local.repository.MoviesLocalRepository
import com.test.domain.useCases.model.MovieModel
import javax.inject.Inject

class AddMovieUseCase @Inject constructor(
    private val repository: MoviesLocalRepository
) {
    suspend fun addMovie(movie: MovieModel) {
        val movieEntity = FavouriteMovieEntity(
            id = movie.id,
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
    suspend fun getAllFavouriteMovies(): List<MovieModel> {
        val favouriteMovies = repository.getAllFavouriteMovies()
        return transformFavouriteMovieEntityToMovieModel(favouriteMovies)
    }

    private fun transformFavouriteMovieEntityToMovieModel(movies: List<FavouriteMovieEntity>): List<MovieModel> {
        val favouriteMoviesList = movies.map {
            MovieModel(
                id = it.id,
                title = it.title,
                description = it.description,
                voteAverage = it.averageRating.toDouble(),
                posterImage = it.imageUrl
            )
        }
        return favouriteMoviesList
    }
}

class AddRecentlySearchedMovieUseCase @Inject constructor(
    private val repository: MoviesLocalRepository
) {
    suspend fun addRecentlySearchedMovie(movie: MovieModel) {
        val movieEntity = RecentlySearchedMovieEntity(
            id = movie.id,
            title = movie.title,
            description = movie.description,
            averageRating = movie.voteAverage.toString(),
            imageUrl = movie.posterImage
        )
        repository.addRecentlySearchedMovie(movieEntity)
    }
}

class GetAllRecentlySearchedMoviesUseCase @Inject constructor(
    private val repository: MoviesLocalRepository
) {
    suspend fun getAllRecentlySearchedMovies(): List<MovieModel> {
        val favouriteMovies = repository.getAllRecentlySearchedMovies()
        return transformRecentlySearchedMovieEntityToMovieModel(favouriteMovies)
    }

    private fun transformRecentlySearchedMovieEntityToMovieModel(movies: List<RecentlySearchedMovieEntity>): List<MovieModel> {
        val favouriteMoviesList = movies.map {
            MovieModel(
                id = it.id,
                title = it.title,
                description = it.description,
                voteAverage = it.averageRating.toDouble(),
                posterImage = it.imageUrl
            )
        }
        return favouriteMoviesList
    }
}