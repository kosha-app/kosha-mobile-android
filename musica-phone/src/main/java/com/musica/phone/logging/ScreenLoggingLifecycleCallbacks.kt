package com.musica.phone.logging

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

private const val TAG = "current-screen"

/**
 * Logs the activity or fragment's name when it is resumed.
 */
internal object ScreenLoggingLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                FragmentLoggingLifecycleCallbacks,
                true
            )
        }
    }

    override fun onActivityStarted(p0: Activity) = Unit

    override fun onActivityResumed(activity: Activity) {
        Log.i(TAG, "[ACTIVITY] ${activity.localClassName}")
    }

    override fun onActivityPaused(p0: Activity) = Unit

    override fun onActivityStopped(p0: Activity) = Unit

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) = Unit

    override fun onActivityDestroyed(p0: Activity) = Unit
}

private object FragmentLoggingLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    @SuppressLint("LogCallWithTag")
    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        Log.i(TAG, "[FRAGMENT] ${f::class.java.name}")

    }
}