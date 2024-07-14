package com.ikurek.scandroid

import android.app.Application
import com.ikurek.scandroid.analytics.Analytics
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ScandroidApplication : Application() {

    @Inject
    lateinit var analytics: Analytics

    override fun onCreate() {
        super.onCreate()
        analytics.initialize()
    }
}
