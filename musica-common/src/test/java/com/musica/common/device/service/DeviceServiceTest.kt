package com.musica.common.device.service

import com.musica.common.device.GetDeviceResponse
import com.musica.common.service.models.response.ResponseType
import com.musica.common.service.models.response.ServiceResponse
import com.musica.common.service.volley.IService
import com.musica.common.service.volley.ServiceResult
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class DeviceServiceImplTest {

    private val mockService = mock(IService::class.java)
    private val deviceService = DeviceServiceImpl(mockService)

    @Test
    fun checkDevice_Successful() = runBlocking {
        // Arrange
        val response = ServiceResult<Void>(ServiceResponse(ResponseType.SUCCESS))
        val deviceId = "123"
        whenever(mockService.GET("device/checkdevice/123", Void::class.java)).thenReturn(response)

        // Act
        val result = deviceService.checkDevice(deviceId)

        // Assert
        verify(mockService).GET("device/checkdevice/123", Void::class.java)
        assertEquals(response, result)
    }

    @Test
    fun getCurrentDevice_Successful() = runBlocking {
        // Arrange
        val response =
            ServiceResult(ServiceResponse(ResponseType.SUCCESS), GetDeviceResponse("user-id"))
        val deviceId = "123"
        whenever(mockService.GET("device/123", GetDeviceResponse::class.java)).thenReturn(response)

        // Act
        val result = deviceService.getCurrentDevice(deviceId)

        // Assert
        verify(mockService).GET("device/123", GetDeviceResponse::class.java)
        assertEquals(response, result)
        assertEquals(response.data.toString(), result.data.toString())
    }

    @Test
    fun logoutDevice_Successful() = runBlocking {
        // Arrange
        val response = ServiceResult<Void>(ServiceResponse(ResponseType.ERROR))
        val deviceId = "123"
        whenever(mockService.PUT("device/logout/123", Void::class.java)).thenReturn(response)

        // Act
        val result = deviceService.logoutDevice(deviceId)

        // Assert
        verify(mockService).PUT("device/logout/123", Void::class.java)
        assertEquals(response, result)
    }


}
