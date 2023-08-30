package com.musica.phone

import android.app.Application
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StarterApplication : Application() {
    companion object {
        private lateinit var requestQueue: RequestQueue

        fun getRequestQueue(): RequestQueue {
            if (!Companion::requestQueue.isInitialized) {
                throw IllegalStateException("Volley RequestQueue not initialized")
            }
            return requestQueue
        }
    }

    override fun onCreate() {
        super.onCreate()
        requestQueue = Volley.newRequestQueue(applicationContext)
    }
}