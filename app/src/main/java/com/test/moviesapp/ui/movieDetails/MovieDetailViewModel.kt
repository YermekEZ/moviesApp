package com.test.moviesapp.ui.movieDetails

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.test.domain.useCases.AddMovieUseCase
import com.test.domain.useCases.GetMovieVideoInfoUseCase
import com.test.domain.useCases.model.MovieModel
import com.test.moviesapp.SamplePeriodicWorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    @ApplicationContext val application: Context,
    private val addMovieUseCase: AddMovieUseCase,
    private val getMovieVideoInfoUseCase: GetMovieVideoInfoUseCase
): ViewModel() {

    companion object {
        const val SAMPLE_PERIODIC_WORK_MANAGER_TAG = "SAMPLE_PERIODIC_WORK_MANAGER_TAG"
    }

    private val _videoKey = MutableLiveData<String>()
    val videoKey: LiveData<String>
        get() = _videoKey

    init {
        startPeriodicWorkManager()
    }

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

    private fun startPeriodicWorkManager() {
        val workRequest = PeriodicWorkRequest.Builder(
            SamplePeriodicWorkManager::class.java,
            15L,
            TimeUnit.MINUTES
        )
            .setConstraints(Constraints.Builder()
                .setRequiresBatteryNotLow(false)
                .build()
            )
            .addTag(SAMPLE_PERIODIC_WORK_MANAGER_TAG)
            .build()

        WorkManager.getInstance(application)
            .enqueueUniquePeriodicWork(
                SAMPLE_PERIODIC_WORK_MANAGER_TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }
}