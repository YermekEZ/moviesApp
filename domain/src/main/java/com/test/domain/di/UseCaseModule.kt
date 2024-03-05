package com.test.domain.di

import com.test.data.network.repository.MoviesRepository
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
}