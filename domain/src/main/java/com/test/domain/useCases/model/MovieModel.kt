package com.test.domain.useCases.model

data class MovieModel(
    val id: Int,
    val title: String,
    val description: String,
    val voteAverage: Double,
    val posterImage: String
)
