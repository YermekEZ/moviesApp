package com.test.moviesapp.ui.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.domain.useCases.AddMovieUseCase
import com.test.domain.useCases.GetMovieVideoInfoUseCase
import com.test.domain.useCases.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val addMovieUseCase: AddMovieUseCase,
    private val getMovieVideoInfoUseCase: GetMovieVideoInfoUseCase
): ViewModel() {

    private val _videoKey = MutableLiveData<String>()
    val videoKey: LiveData<String>
        get() = _videoKey

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
}