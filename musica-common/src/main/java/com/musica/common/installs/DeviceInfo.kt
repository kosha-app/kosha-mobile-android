package com.musica.common.installs

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject


class DeviceInfo @Inject constructor( private val context: Context) {

    private fun isFirstInstall(): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isFirstInstall = prefs.getBoolean(KEY_FIRST_INSTALL, true)
        if (isFirstInstall) {
            prefs.edit().putBoolean(KEY_FIRST_INSTALL, false).apply()
        }
        return isFirstInstall
    }

    fun deviceId() = if (isFirstInstall()) {
        val deviceId = UUID.randomUUID().toString()
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_DEVICE_ID, deviceId).apply()
        deviceId
    } else {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.getString(KEY_DEVICE_ID, null)
    }


    companion object {
        private const val PREFS_NAME = "KoshaAppPrefs"
        private const val KEY_FIRST_INSTALL = "first_install"
        private const val KEY_DEVICE_ID = "device_id"
    }
}
