package com.test.moviesapp.sharedPrefs

import android.content.Context
import android.content.SharedPreferences

object SharedPreferences {

    fun getSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }
}