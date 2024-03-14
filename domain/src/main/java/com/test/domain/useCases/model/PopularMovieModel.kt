package com.test.domain.useCases.model

data class PopularMovieModel(
    val title: String,
    val description: String,
    val voteAverage: Double,
    val posterImage: String
)
