package com.musica.common.device.service

import android.app.Service
import com.musica.common.device.CheckDeviceResponse
import com.musica.common.service.volley.IService
import com.musica.common.service.volley.ServiceException
import com.musica.common.service.volley.ServiceResult
import javax.inject.Inject

interface DeviceService{

    suspend fun checkDevice(deviceId: String): ServiceResult<CheckDeviceResponse>
}

const val CHECK_DEVICE_URL = "device/checkdeviceV2/%s"

class DeviceServiceImpl @Inject constructor(
    private val service: IService
):DeviceService {

    override suspend fun checkDevice(deviceId: String): ServiceResult<CheckDeviceResponse> {
        return service.GET(CHECK_DEVICE_URL.format(deviceId), CheckDeviceResponse::class.java)
    }
}