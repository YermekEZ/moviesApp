package com.test.moviesapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import com.test.moviesapp.sharedPrefs.SharedPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApp : Application(), ImageLoaderFactory {

    companion object {
        const val coilDiskCacheSize: Long = 1024 * 1024 * 1024 // 1 Gb
    }

    override fun onCreate() {
        super.onCreate()
        if (!SharedPreferences.getSharedPreference(this).contains("IS_FIRST_LAUNCH")) {
            SharedPreferences.getSharedPreference(this).edit().putBoolean("IS_FIRST_LAUNCH", true).apply()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "service_channel",
                "Foreground service notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.1)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizeBytes(coilDiskCacheSize)
                    .directory(cacheDir)
                    .build()
            }
            .build()
    }
}