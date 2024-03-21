package com.test.moviesapp.ui.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.domain.useCases.AddMovieUseCase
import com.test.domain.useCases.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val addMovieUseCase: AddMovieUseCase
): ViewModel() {

    fun addMovieToFavorites(movie: MovieModel) {
        viewModelScope.launch {
            addMovieUseCase.addMovie(movie)
        }
    }
}