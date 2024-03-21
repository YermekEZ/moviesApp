package com.test.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recentlySearched")
data class RecentlySearchedMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val averageRating: String,
    val imageUrl: String
)
