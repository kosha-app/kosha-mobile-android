package com.musica.dashboard.module;

import android.media.MediaPlayer;

import com.musica.dashboard.player.repository.TrackRepository;
import com.musica.dashboard.player.repository.TrackRepositoryImpl;
import com.musica.dashboard.player.service.TrackService;
import com.musica.dashboard.player.service.TrackServiceImpl;
import com.musica.dashboard.user.repository.UserRepository;
import com.musica.dashboard.user.repository.UserRepositoryImpl;
import com.musica.dashboard.user.service.UserService;
import com.musica.dashboard.user.service.UserServiceImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn({SingletonComponent.class})
public interface DashboardModule {

    @Binds
    TrackService bindsTrackService(TrackServiceImpl trackService);

    @Binds
    TrackRepository bindsTrackRepository(TrackRepositoryImpl trackRepository);

    @Binds
    UserService bindUserService(UserServiceImpl impl);

    @Binds
    UserRepository bindUserRepository(UserRepositoryImpl impl);

    @Provides
    static MediaPlayer providesService(){return new MediaPlayer();}
}

