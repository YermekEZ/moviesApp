package com.test.moviesapp.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.domain.useCases.GetAllFavouriteMoviesUseCase
import com.test.domain.useCases.model.PopularMovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteMoviesViewModel @Inject constructor(
    private val getAllFavouriteMoviesUseCase: GetAllFavouriteMoviesUseCase
): ViewModel() {

    private val _favouriteMovies = MutableLiveData<List<PopularMovieModel>>()
    val favouriteMovies: LiveData<List<PopularMovieModel>>
        get() = _favouriteMovies

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            _favouriteMovies.value = getAllFavouriteMoviesUseCase.getAllFavouriteMovies()
        }
    }
}