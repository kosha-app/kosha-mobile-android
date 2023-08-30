package com.musica.common.module;

import com.musica.common.device.repository.DeviceRepository;
import com.musica.common.device.repository.DeviceRepositoryImpl;
import com.musica.common.device.service.DeviceService;
import com.musica.common.device.service.DeviceServiceImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn({SingletonComponent.class})
public interface CommonModule {


    @Binds
    DeviceRepository bindDeviceRepository(DeviceRepositoryImpl impl);

    @Binds
    DeviceService bindDeviceService(DeviceServiceImpl impl);
}
