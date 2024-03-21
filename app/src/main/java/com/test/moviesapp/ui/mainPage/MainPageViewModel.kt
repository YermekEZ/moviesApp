package com.test.moviesapp.ui.mainPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.test.domain.useCases.AddMovieUseCase
import com.test.domain.useCases.GetPagedPopularMoviesUseCase
import com.test.domain.useCases.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val getPagedPopularMoviesUseCase: GetPagedPopularMoviesUseCase,
    private val addMovieUseCase: AddMovieUseCase
) : ViewModel() {

    fun getPagedMovies(): LiveData<PagingData<MovieModel>> {
        return getPagedPopularMoviesUseCase.getPagedPopularMovies("5b81ffe30455492a85958994a1275dd1").map {
            it.map {
                MovieModel(
                    id = it.id,
                    title = it.title ?: "Unknown title",
                    description = it.overview ?: "No description",
                    voteAverage = it.voteAverage ?: 0.0,
                    posterImage = it.posterPath.orEmpty()
                )
            }
        }.cachedIn(viewModelScope)
    }

    fun saveMovie(movie: MovieModel) {
        viewModelScope.launch {
            addMovieUseCase.addMovie(movie)
        }
    }
}