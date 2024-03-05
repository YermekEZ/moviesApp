package com.test.moviesapp.ui.mainPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.domain.useCases.GetPopularMoviesUseCase
import com.test.domain.useCases.model.PopularMovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<List<PopularMovieModel>>()
    val movies: LiveData<List<PopularMovieModel>>
        get() = _movies

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            val result = getPopularMoviesUseCase.getPopularMovies("5b81ffe30455492a85958994a1275dd1")
            _movies.value = result
        }
    }
}