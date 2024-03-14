package com.test.domain.useCases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test.data.network.model.Movie
import com.test.data.network.repository.MoviesRepository
import javax.inject.Inject

class GetPagedPopularMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    fun getPagedPopularMovies(apiKey: String): LiveData<PagingData<Movie>> {
        return repository.getPagedPopularMovies(apiKey)
    }

}