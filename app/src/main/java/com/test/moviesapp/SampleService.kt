package com.test.moviesapp

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.test.domain.useCases.GetAllFavouriteMoviesUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SampleService : Service() {

    @Inject
    lateinit var getAllFavouriteMoviesUseCase: GetAllFavouriteMoviesUseCase

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    enum class ServiceAction {
        START, STOP
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    /**
     * startForeground() should be called within 10 seconds after onStartCommand() or app will crash
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ServiceAction.START.toString() -> start()
            ServiceAction.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        scope.launch {
            val moviesList = getAllFavouriteMoviesUseCase.getAllFavouriteMovies()
            val lastTitle = moviesList.last().title
            val notification = NotificationCompat.Builder(this@SampleService, "service_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Service is running...")
                .setContentText(lastTitle)
                .build()
            startForeground(1, notification)
        }
//        val notification = NotificationCompat.Builder(this, "service_channel")
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("Service is running...")
//            .setContentText("Some work is beind done")
//            .build()
//        startForeground(1, notification)
    }

    override fun startService(service: Intent?): ComponentName? {
        return super.startService(service)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}