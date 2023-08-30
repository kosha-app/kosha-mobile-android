package com.musica.phone.servicelayer;

import com.musica.common.service.volley.IService;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn({SingletonComponent.class})
public interface ServiceModule {


    @Provides
    static IService providesService(){return new Service();}
}
