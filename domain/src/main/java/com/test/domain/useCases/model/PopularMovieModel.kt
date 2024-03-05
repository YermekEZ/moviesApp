package com.test.domain.useCases.model

data class PopularMovieModel(
    val adult: Boolean,
    val title: String,
    val voteAverage: Double,
    val posterImage: String
)
