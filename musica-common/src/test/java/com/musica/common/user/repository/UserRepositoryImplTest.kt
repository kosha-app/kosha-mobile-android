package com.musica.common.user.repository

import com.musica.common.service.models.response.DefaultResponse
import com.musica.common.service.models.response.ResponseType
import com.musica.common.service.models.response.*
import com.musica.common.service.volley.ServiceResult
import com.musica.common.user.service.UserService
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserRepositoryImplTest {

    @Mock
    private lateinit var mockService: UserService

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userRepository = UserRepositoryImpl(mockService)
    }

    @Test
    fun resendOtp_Successful() = runBlocking {
        // Arrange
        val response = ServiceResult<DefaultResponse>(ServiceResponse(ResponseType.SUCCESS))
        val email = "email@test.com"
        whenever(mockService.resendOtp(email)).thenReturn(response)

        // Act
        val result = userRepository.resendOtp(email)

        // Assert
        Mockito.verify(mockService).resendOtp(email)
        Assert.assertEquals(response, result)
    }

    @Test
    fun checkEmail_Successful() = runBlocking {
        // Arrange
        val response = ServiceResult<CheckEmailResponse>(ServiceResponse(ResponseType.SUCCESS))
        val email = "email@test.com"
        whenever(mockService.checkEmail(email)).thenReturn(response)

        // Act
        val result = userRepository.checkEmail(email)

        // Assert
        Mockito.verify(mockService).checkEmail(email)
        Assert.assertEquals(response, result)
    }

    @Test
    fun getUserProfile_Successful() = runBlocking {
        // Arrange
        val response = ServiceResult<GetUserInfoResponse>(ServiceResponse(ResponseType.SUCCESS))
        val id = "123"
        whenever(mockService.getUserProfile(id)).thenReturn(response)

        // Act
        val result = userRepository.getUserProfile(id)

        // Assert
        Mockito.verify(mockService).getUserProfile(id)
        Assert.assertEquals(response, result)
    }
}