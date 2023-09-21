package com.musica.common.installs;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn({SingletonComponent.class})
public class InstallModule {


    @Provides
    static DeviceInfo installationManager(@ApplicationContext Context context) {
        return new DeviceInfo(context);
    }
}
