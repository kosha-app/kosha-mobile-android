package com.musica.phone.servicelayer;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.musica.common.service.volley.IService;
import com.musica.phone.interceptor.LoggingInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;

@Module
@InstallIn({SingletonComponent.class})
public interface ServiceModule {


    @Provides
    static IService providesService(@ApplicationContext Context context) {
        return new Service(provideRequestQueue(context));
    }

    @Singleton
    @Provides
    static RequestQueue provideRequestQueue(@ApplicationContext Context context) {
        LoggingInterceptor interceptor = new LoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        return Volley.newRequestQueue(context, new OkHttpStack(client));
    }

}
