package com.test.data.local.repository

import com.test.data.local.dao.MoviesDao
import com.test.data.local.model.FavouriteMovieEntity
import com.test.data.local.model.RecentlySearchedMovieEntity
import javax.inject.Inject

class MoviesLocalRepositoryImpl @Inject constructor(
    private val dao: MoviesDao
): MoviesLocalRepository {

    override suspend fun addMovie(movie: FavouriteMovieEntity) {
        return dao.addMovie(movie)
    }

    override suspend fun removeMovieFromFavorites(movieID: Int) {
        return dao.removeMovie(movieID)
    }

    override suspend fun getAllFavouriteMovies(): List<FavouriteMovieEntity> {
        return dao.getAllFavouriteMovies()
    }

    override suspend fun isMovieFavorite(movieID: Int): Boolean {
        val favoriteMovie: FavouriteMovieEntity? = dao.getFavouriteMovieByID(movieID)
        return favoriteMovie != null
    }

    override suspend fun addRecentlySearchedMovie(movie: RecentlySearchedMovieEntity) {
        return dao.addRecentlySearchedMovie(movie)
    }

    override suspend fun getAllRecentlySearchedMovies(): List<RecentlySearchedMovieEntity> {
        return dao.getAllRecentlySearchedMovies()
    }
}