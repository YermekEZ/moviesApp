package com.test.data.network.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.data.network.MoviesService
import com.test.data.network.model.Movie
import javax.inject.Inject

class MovieListPagingSource @Inject constructor(
    private val api: MoviesService
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val result = api.getPopularMovies("5b81ffe30455492a85958994a1275dd1", page)
            val moviesList = result.body()?.results ?: emptyList()
            LoadResult.Page(
                data = moviesList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (moviesList.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 1
    }

}