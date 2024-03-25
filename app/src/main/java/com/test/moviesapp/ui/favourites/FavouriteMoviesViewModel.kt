package com.test.moviesapp.ui.favourites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.domain.useCases.GetAllFavouriteMoviesUseCase
import com.test.domain.useCases.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteMoviesViewModel @Inject constructor(
    private val getAllFavouriteMoviesUseCase: GetAllFavouriteMoviesUseCase
): ViewModel() {

    private val _favouriteMovies = MutableLiveData<List<MovieModel>>()
    val favouriteMovies: LiveData<List<MovieModel>>
        get() = _favouriteMovies

    fun getMovies() {
        viewModelScope.launch {
            val test = getAllFavouriteMoviesUseCase.getAllFavouriteMovies()
            test.forEach {
                Log.d("MOVIES_LIST", it.toString())
            }
            _favouriteMovies.value = test//getAllFavouriteMoviesUseCase.getAllFavouriteMovies()
        }
    }
}