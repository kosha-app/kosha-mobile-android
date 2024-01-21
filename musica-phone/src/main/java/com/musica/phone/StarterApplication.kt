package com.musica.phone

import android.app.Application
import android.content.pm.ApplicationInfo
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import com.musica.phone.logging.ScreenLoggingLifecycleCallbacks
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.internal.Contexts.getApplication


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
        AppCenter.start(
            getApplication(applicationContext), "33972015-3d6b-4401-b7d0-3c0bb92bfe88",
            Analytics::class.java, Crashes::class.java
        )
        AppCenter.start(
            getApplication(applicationContext),
            "33972015-3d6b-4401-b7d0-3c0bb92bfe88",
            Distribute::class.java
        )
    }
}