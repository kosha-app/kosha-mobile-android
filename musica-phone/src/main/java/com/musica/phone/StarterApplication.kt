package com.musica.phone

import android.app.Application
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.internal.Contexts.getApplication


@HiltAndroidApp
class StarterApplication : Application() {

    override fun onCreate() {

        super.onCreate()
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