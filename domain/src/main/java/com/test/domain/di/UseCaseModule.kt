package com.test.domain.di

import com.test.data.local.repository.MoviesLocalRepository
import com.test.data.network.repository.MoviesRepository
import com.test.domain.useCases.AddMovieUseCase
import com.test.domain.useCases.GetAllFavouriteMoviesUseCase
import com.test.domain.useCases.GetPagedPopularMoviesUseCase
import com.test.domain.useCases.GetPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetPopularMoviesUseCase(repository: MoviesRepository): GetPopularMoviesUseCase = GetPopularMoviesUseCase(repository)

    @Provides
    fun provideGetPagedPopularMoviesUseCase(repository: MoviesRepository): GetPagedPopularMoviesUseCase = GetPagedPopularMoviesUseCase(repository)
}

@Module
@InstallIn(SingletonComponent::class)
object LocalUseCaseModule {

    @Provides
    fun provideAddMovieUseCase(repository: MoviesLocalRepository): AddMovieUseCase = AddMovieUseCase(repository)

    @Provides
    fun provideGetAllFavouriteMovies(repository: MoviesLocalRepository): GetAllFavouriteMoviesUseCase = GetAllFavouriteMoviesUseCase(repository)
}