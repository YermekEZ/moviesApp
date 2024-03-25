package com.test.domain.di

import com.test.data.local.repository.MoviesLocalRepository
import com.test.data.network.repository.MoviesRepository
import com.test.domain.useCases.AddMovieUseCase
import com.test.domain.useCases.AddRecentlySearchedMovieUseCase
import com.test.domain.useCases.GetAllFavouriteMoviesUseCase
import com.test.domain.useCases.GetAllRecentlySearchedMoviesUseCase
import com.test.domain.useCases.GetMovieVideoInfoUseCase
import com.test.domain.useCases.GetPagedPopularMoviesUseCase
import com.test.domain.useCases.SearchMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideSearchMoviesUseCase(repository: MoviesRepository): SearchMoviesUseCase = SearchMoviesUseCase(repository)

    @Provides
    fun provideGetPagedPopularMoviesUseCase(repository: MoviesRepository): GetPagedPopularMoviesUseCase = GetPagedPopularMoviesUseCase(repository)

    @Provides
    fun provideGetMovieVideoInfoUseCase(repository: MoviesRepository): GetMovieVideoInfoUseCase = GetMovieVideoInfoUseCase(repository)
}

@Module
@InstallIn(SingletonComponent::class)
object LocalUseCaseModule {

    @Provides
    fun provideAddMovieUseCase(repository: MoviesLocalRepository): AddMovieUseCase = AddMovieUseCase(repository)

    @Provides
    fun provideGetAllFavouriteMovies(repository: MoviesLocalRepository): GetAllFavouriteMoviesUseCase = GetAllFavouriteMoviesUseCase(repository)

    @Provides
    fun provideAddRecentlySearchedMovieUseCase(repository: MoviesLocalRepository): AddRecentlySearchedMovieUseCase = AddRecentlySearchedMovieUseCase(repository)

    @Provides
    fun provideGetAllRecentlySearchedMoviesUseCase(repository: MoviesLocalRepository): GetAllRecentlySearchedMoviesUseCase = GetAllRecentlySearchedMoviesUseCase(repository)
}