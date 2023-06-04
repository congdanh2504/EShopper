package com.training.eshopper

import android.app.Application
import com.training.eshopper.utils.AppPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
    }
}