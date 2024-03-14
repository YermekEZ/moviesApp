package com.test.data.local.di

import android.app.Application
import androidx.room.Room
import com.test.data.local.database.MoviesDatabase
import com.test.data.local.repository.MoviesLocalRepository
import com.test.data.local.repository.MoviesLocalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMoviesDatabase(app: Application): MoviesDatabase {
        return Room.databaseBuilder(
            app,
            MoviesDatabase::class.java,
            MoviesDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideMoviesLocalRepository(db: MoviesDatabase): MoviesLocalRepository {
        return MoviesLocalRepositoryImpl(db.moviesDao)
    }
}