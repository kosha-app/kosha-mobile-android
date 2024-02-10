package com.musica.common.device.repository

import com.musica.common.service.models.response.GetDeviceResponse
import com.musica.common.device.service.DeviceService
import com.musica.common.service.volley.ServiceResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DeviceRepository {
    suspend fun checkDevice(deviceId: String): ServiceResult<Void>

    suspend fun logoutDevice(deviceId: String): ServiceResult<Void>

    suspend fun getCurrentDevice(deviceId: String): ServiceResult<GetDeviceResponse>

}

class DeviceRepositoryImpl @Inject constructor(
    private val deviceService: DeviceService
): DeviceRepository {
    override suspend fun checkDevice(deviceId: String) = withContext(Dispatchers.IO) {
        deviceService.checkDevice(deviceId)
    }

    override suspend fun logoutDevice(deviceId: String) = withContext(Dispatchers.IO) {
        deviceService.logoutDevice(deviceId)
    }

    override suspend fun getCurrentDevice(deviceId: String) = withContext(Dispatchers.IO) {
        deviceService.getCurrentDevice(deviceId)
    }

}
