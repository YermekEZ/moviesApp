package com.test.moviesapp.ui.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.domain.useCases.AddMovieUseCase
import com.test.domain.useCases.GetMovieVideoInfoUseCase
import com.test.domain.useCases.RemoveMovieUseCase
import com.test.domain.useCases.CheckIsMovieFavoriteUseCase
import com.test.domain.useCases.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val addMovieUseCase: AddMovieUseCase,
    private val removeMovieUseCase: RemoveMovieUseCase,
    private val getMovieVideoInfoUseCase: GetMovieVideoInfoUseCase,
    private val isMovieFavoriteUseCase: CheckIsMovieFavoriteUseCase
): ViewModel() {

    private val _videoKey = MutableLiveData<String>()
    private val _isFavorite = MutableLiveData<Boolean>()
    val videoKey: LiveData<String>
        get() = _videoKey
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    fun getVideoInfo(movieId: String) {
        viewModelScope.launch {
            val videoInfo = getMovieVideoInfoUseCase.getMovieVideoInfo(movieId)
            _videoKey.value = videoInfo.firstOrNull()?.key
        }
    }

    fun addMovieToFavorites(movie: MovieModel) {
        viewModelScope.launch {
            addMovieUseCase.addMovie(movie)
        }
    }

    fun removeMovieFromFavorites(movie: MovieModel) {
        viewModelScope.launch {
            removeMovieUseCase.removeMovieFromFavorites(movie)
        }
    }

    fun isMovieFavorite(movie: MovieModel) {
        viewModelScope.launch {
            _isFavorite.value = isMovieFavoriteUseCase.isMovieFavorite(movie)
        }
    }
}