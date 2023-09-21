package com.musica.common.device.service

import android.app.Service
import com.musica.common.device.CheckDeviceResponse
import com.musica.common.device.GetDeviceResponse
import com.musica.common.service.models.response.DefaultResponse
import com.musica.common.service.volley.IService
import com.musica.common.service.volley.ServiceException
import com.musica.common.service.volley.ServiceResult
import javax.inject.Inject

interface DeviceService {

    suspend fun checkDevice(deviceId: String): ServiceResult<CheckDeviceResponse>

    suspend fun logoutDevice(deviceId: String): ServiceResult<DefaultResponse>

    suspend fun getCurrentDevice(deviceId: String): ServiceResult<GetDeviceResponse>
}

const val CHECK_DEVICE_URL = "device/checkdevice/%s"
const val LOGOUT_DEVICE_URL = "device/logout/%s"
const val GET_DEVICE_URL = "device/%s"

class DeviceServiceImpl @Inject constructor(
    private val service: IService
) : DeviceService {

    override suspend fun checkDevice(deviceId: String): ServiceResult<CheckDeviceResponse> {
        return service.GET(CHECK_DEVICE_URL.format(deviceId), CheckDeviceResponse::class.java)
    }

    override suspend fun logoutDevice(deviceId: String): ServiceResult<DefaultResponse> {
        return service.PUT(LOGOUT_DEVICE_URL.format(deviceId), DefaultResponse::class.java)

    }

    override suspend fun getCurrentDevice(deviceId: String): ServiceResult<GetDeviceResponse> {
        return service.GET(GET_DEVICE_URL.format(deviceId), GetDeviceResponse::class.java)
    }
}