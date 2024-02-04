package com.musica.phone

import android.app.Application
import android.content.pm.ApplicationInfo
import com.musica.phone.logging.ScreenLoggingLifecycleCallbacks
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class StarterApplication : Application() {
    private var isDebugMode = false

    override fun onCreate() {
        super.onCreate()
        applicationContext?.applicationInfo?.let {
            isDebugMode = (0 != it.flags and ApplicationInfo.FLAG_DEBUGGABLE)
        }

        if (isDebugMode) {

            registerActivityLifecycleCallbacks(ScreenLoggingLifecycleCallbacks)
        }
    }
}