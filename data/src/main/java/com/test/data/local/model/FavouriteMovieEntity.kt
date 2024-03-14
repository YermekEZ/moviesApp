package com.test.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favouriteMovies")
data class FavouriteMovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val averageRating: String,
    val imageUrl: String
)