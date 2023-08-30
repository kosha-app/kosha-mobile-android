package com.musica.common.device.repository

import com.musica.common.device.CheckDeviceResponse
import com.musica.common.device.service.DeviceService
import com.musica.common.service.volley.ServiceResult
import javax.inject.Inject

interface DeviceRepository {
    suspend fun checkDevice(deviceId: String): ServiceResult<CheckDeviceResponse>
}

class DeviceRepositoryImpl @Inject constructor(
    private val deviceService: DeviceService
): DeviceRepository {
    override suspend fun checkDevice(deviceId: String): ServiceResult<CheckDeviceResponse> {
        return deviceService.checkDevice(deviceId)
    }

}
