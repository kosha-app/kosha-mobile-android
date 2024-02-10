package com.musica.common.device.repository

import com.musica.common.service.models.response.GetDeviceResponse
import com.musica.common.device.service.DeviceService
import com.musica.common.service.models.response.ResponseType
import com.musica.common.service.models.response.ServiceResponse
import com.musica.common.service.volley.ServiceResult
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class DeviceRepositoryImplTest {

    private val service = mock(DeviceService::class.java)
    private val deviceRepo = DeviceRepositoryImpl(service)

    @Test
    fun checkDevice_Successful() = runBlocking {
        // Arrange
        val response = ServiceResult<Void>(ServiceResponse(ResponseType.SUCCESS))
        val deviceId = "123"
        whenever(service.checkDevice(deviceId)).thenReturn(response)

        // Act
        val result = deviceRepo.checkDevice(deviceId)

        // Assert
        verify(service).checkDevice(deviceId)
        assertEquals(response, result)
    }

    @Test
    fun getCurrentDevice_Successful() = runBlocking {
        // Arrange
        val response =
            ServiceResult(ServiceResponse(ResponseType.SUCCESS), GetDeviceResponse("user-id"))
        val deviceId = "123"
        whenever(service.getCurrentDevice(deviceId)).thenReturn(response)

        // Act
        val result = deviceRepo.getCurrentDevice(deviceId)

        // Assert
        verify(service).getCurrentDevice(deviceId)
        assertEquals(response, result)
        assertEquals(response.data.toString(), result.data.toString())
    }

    @Test
    fun logoutDevice_Successful() = runBlocking {
        // Arrange
        val response = ServiceResult<Void>(ServiceResponse(ResponseType.ERROR))
        val deviceId = "123"
        whenever(service.logoutDevice(deviceId)).thenReturn(response)

        // Act
        val result = deviceRepo.logoutDevice(deviceId)

        // Assert
        verify(service).logoutDevice(deviceId)
        assertEquals(response, result)
    }
}