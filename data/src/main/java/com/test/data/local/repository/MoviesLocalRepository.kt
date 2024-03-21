package com.test.data.local.repository

import com.test.data.local.model.FavouriteMovieEntity
import com.test.data.local.model.RecentlySearchedMovieEntity

interface MoviesLocalRepository {

    suspend fun addMovie(movie: FavouriteMovieEntity)

    suspend fun getAllFavouriteMovies(): List<FavouriteMovieEntity>

    suspend fun addRecentlySearchedMovie(movie: RecentlySearchedMovieEntity)

    suspend fun getAllRecentlySearchedMovies(): List<RecentlySearchedMovieEntity>
}