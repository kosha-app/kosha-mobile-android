package com.musica.common.device

import android.content.Context
import android.provider.Settings

object DeviceInfo{
    fun deviceId(context: Context) = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}