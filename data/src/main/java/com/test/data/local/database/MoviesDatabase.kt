package com.test.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.data.local.dao.MoviesDao
import com.test.data.local.model.FavouriteMovieEntity
import com.test.data.local.model.RecentlySearchedMovieEntity

@Database(
    entities = [
        FavouriteMovieEntity::class,
        RecentlySearchedMovieEntity::class
    ],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "movies_db"
    }

    abstract val moviesDao: MoviesDao
}