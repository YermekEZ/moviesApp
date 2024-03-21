package com.test.moviesapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.domain.useCases.AddRecentlySearchedMovieUseCase
import com.test.domain.useCases.GetAllRecentlySearchedMoviesUseCase
import com.test.domain.useCases.SearchMoviesUseCase
import com.test.domain.useCases.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val addRecentlySearchedMovieUseCase: AddRecentlySearchedMovieUseCase,
    private val getAllRecentlySearchedMoviesUseCase: GetAllRecentlySearchedMoviesUseCase
): ViewModel() {

    private val _moviesListFromSearch = MutableLiveData<List<MovieModel>>()
    val moviesListFromSearch: LiveData<List<MovieModel>>
        get() = _moviesListFromSearch

    private val _recentlySearchedMovies = MutableLiveData<List<MovieModel>>()
    val recentlySearchedMovies: LiveData<List<MovieModel>>
        get() = _recentlySearchedMovies

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _moviesListFromSearch.value = searchMoviesUseCase.searchMovies(query)
        }
    }

    fun addMovieToRecentlySearched(movie: MovieModel) {
        viewModelScope.launch {
            addRecentlySearchedMovieUseCase.addRecentlySearchedMovie(movie)
        }
    }

    fun getRecentlySearchedMovies() {
        viewModelScope.launch {
            _recentlySearchedMovies.value = getAllRecentlySearchedMoviesUseCase.getAllRecentlySearchedMovies()
        }
    }
}