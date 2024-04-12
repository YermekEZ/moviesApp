package com.test.moviesapp

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.test.moviesapp.notification.LocalNotification

class SamplePeriodicWorkManager(
    val context: Context,
    params: WorkerParameters
): Worker(context, params) {
    override fun doWork(): Result {
        try {
            LocalNotification(context).showNotification("Periodic work request", "Successfully run work request")
            return Result.success()
        } catch (error: Throwable) {
            return Result.retry()
        }
    }
//        override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
//        try {
//            // do something
//            return@withContext Result.success()
//        } catch (error: Throwable) {
//            return@withContext Result.retry()
//        }
//    }

}