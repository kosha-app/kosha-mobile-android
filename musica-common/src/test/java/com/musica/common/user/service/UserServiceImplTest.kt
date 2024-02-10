package com.musica.common.user.service

import com.musica.common.service.models.response.DefaultResponse
import com.musica.common.service.models.request.*
import com.musica.common.service.models.response.*
import com.musica.common.service.volley.IService
import com.musica.common.service.volley.ServiceResult
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class UserServiceImplTest {

    private val mockService = Mockito.mock(IService::class.java)
    private val userService = UserServiceImpl(mockService)

    @Test
    fun userSignIn_Successful() = runBlocking {
        // Arrange
        val response = ServiceResult<SignInResponse>(ServiceResponse(ResponseType.SUCCESS))
        val deviceId = "123"
        val request = UserSignInRequest("test@email.com", "password", deviceId)
        whenever(mockService.POST("user/signin", request, SignInResponse::class.java)).thenReturn(
            response
        )

        // Act
        val result = userService.userSignIn(request)

        // Assert
        Mockito.verify(mockService).POST("user/signin", request, SignInResponse::class.java)
        Assert.assertEquals(response, result)
    }

    @Test
    fun registerUser_Successful() = runBlocking {
        // Arrange
        val response = ServiceResult<DefaultResponse>(ServiceResponse(ResponseType.SUCCESS))
        val deviceId = "123"
        val request = UserRegistrationRequest(
            "name",
            "password",
            "test@email.com",
            "20 July 2012",
            "Male",
            deviceId
        )
        whenever(
            mockService.POST(
                "user/register",
                request,
                DefaultResponse::class.java
            )
        ).thenReturn(response)

        // Act
        val result = userService.registerUser(request)

        // Assert
        Mockito.verify(mockService).POST("user/register", request, DefaultResponse::class.java)
        Assert.assertEquals(response, result)
    }

    @Test
    fun resendOtp_Successful() = runBlocking {
        // Arrange
        val response = ServiceResult<DefaultResponse>(ServiceResponse(ResponseType.SUCCESS))
        val email = "email@test.com"
        whenever(
            mockService.POST(
                "user/resendOtp/email@test.com",
                DefaultResponse::class.java
            )
        ).thenReturn(response)

        // Act
        val result = userService.resendOtp(email)

        // Assert
        Mockito.verify(mockService)
            .POST("user/resendOtp/email@test.com", DefaultResponse::class.java)
        Assert.assertEquals(response, result)
    }

    @Test
    fun checkEmail_Successful() = runBlocking {
        // Arrange
        val response = ServiceResult<CheckEmailResponse>(ServiceResponse(ResponseType.SUCCESS))
        val email = "email@test.com"
        whenever(
            mockService.POST(
                "user/checkemail/email@test.com",
                CheckEmailResponse::class.java
            )
        ).thenReturn(response)

        // Act
        val result = userService.checkEmail(email)

        // Assert
        Mockito.verify(mockService)
            .POST("user/checkemail/email@test.com", CheckEmailResponse::class.java)
        Assert.assertEquals(response, result)
    }

    @Test
    fun verifyOtp_Successful() = runBlocking {
        // Arrange
        val response = ServiceResult<Void>(ServiceResponse(ResponseType.SUCCESS))
        val id = "123"
        val request = UserVerificationRequest("123456")
        whenever(mockService.POST("user/verification/123", request, Void::class.java)).thenReturn(
            response
        )

        // Act
        val result = userService.verifyOtp(id, request)

        // Assert
        Mockito.verify(mockService).POST("user/verification/123", request, Void::class.java)
        Assert.assertEquals(response, result)
    }

    @Test
    fun getUserProfile_Successful() = runBlocking {
        // Arrange
        val response = ServiceResult<GetUserInfoResponse>(ServiceResponse(ResponseType.SUCCESS))
        val id = "123"
        whenever(mockService.GET("user/profile/123", GetUserInfoResponse::class.java)).thenReturn(
            response
        )

        // Act
        val result = userService.getUserProfile(id)

        // Assert
        Mockito.verify(mockService).GET("user/profile/123", GetUserInfoResponse::class.java)
        Assert.assertEquals(response, result)
    }
}