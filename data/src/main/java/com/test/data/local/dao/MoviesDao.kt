package com.test.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.data.local.model.FavouriteMovieEntity
import com.test.data.local.model.RecentlySearchedMovieEntity

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: FavouriteMovieEntity)

    @Query("DELETE FROM favouriteMovies WHERE id=:movieID")
    suspend fun removeMovie(movieID: Int)

    @Query("SELECT * FROM favouriteMovies")
    suspend fun getAllFavouriteMovies(): List<FavouriteMovieEntity>

    @Query("SELECT * FROM favouriteMovies WHERE id=:movieID")
    suspend fun getFavouriteMovieByID(movieID: Int): FavouriteMovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecentlySearchedMovie(movie: RecentlySearchedMovieEntity)

    @Query("SELECT * FROM recentlySearched")
    suspend fun getAllRecentlySearchedMovies(): List<RecentlySearchedMovieEntity>
}