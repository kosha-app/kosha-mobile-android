package com.musica.common.installs

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface InstallModule {

    companion object {
        @Provides
        fun installationManager(
            @ApplicationContext context: Context
        ): DeviceInfo {
            return DeviceInfo(context)
        }
    }
}
