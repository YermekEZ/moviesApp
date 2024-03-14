package com.test.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.data.local.dao.MoviesDao
import com.test.data.local.model.FavouriteMovieEntity

@Database(
    entities = [FavouriteMovieEntity::class],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "movies_db"
    }

    abstract val moviesDao: MoviesDao
}