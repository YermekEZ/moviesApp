package com.test.data.local.repository

import com.test.data.local.model.FavouriteMovieEntity

interface MoviesLocalRepository {

    suspend fun addMovie(movie: FavouriteMovieEntity)

    suspend fun getAllFavouriteMovies(): List<FavouriteMovieEntity>
}