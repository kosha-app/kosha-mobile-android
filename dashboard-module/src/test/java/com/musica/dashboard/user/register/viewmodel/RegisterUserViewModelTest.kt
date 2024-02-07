package com.musica.dashboard.user.register.viewmodel

import android.app.Application
import app.cash.turbine.test
import com.musica.common.installs.DeviceInfo
import com.musica.common.service.models.response.DefaultResponse
import com.musica.common.service.models.response.ResponseType
import com.musica.common.service.models.response.ServiceResponse
import com.musica.common.service.volley.ServiceResult
import com.musica.common.user.CheckEmailResponse
import com.musica.common.user.repository.UserRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterUserViewModelTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var deviceInfo: DeviceInfo

    @InjectMocks
    private lateinit var viewModel: RegisterUserViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher after each test
        Dispatchers.resetMain()
    }

    @Test
    fun `checkEmail should emit success navigation event on successful response`() = runTest {
        val email = "test@example.com"
        val successResponse = ServiceResult(
            ServiceResponse(ResponseType.SUCCESS),
            CheckEmailResponse("id", "")
        )

        whenever(userRepository.checkEmail(email)).thenReturn(successResponse)

        viewModel.emailCheckSuccessNav.test {
            viewModel.checkEmail(email)

            val navString = awaitItem()

            Assert.assertSame("otp-capture", navString)
        }
    }

    @Test
    fun `checkEmail should emit error message on unsuccessful response`() = runTest {
        val email = "test@example.com"
        val errorResponse = ServiceResult<CheckEmailResponse>(
            ServiceResponse(
                ResponseType.ERROR,
                "",
                "Something went wrong"
            )
        )

        whenever(userRepository.checkEmail(email)).thenReturn(errorResponse)

        viewModel.errorMessage.test {
            viewModel.checkEmail(email)

            val error = awaitItem()

            assertEquals(errorResponse.serviceResponse.message, error)
        }
    }

    @Test
    fun `registerUser should call deviceInfo deviceID`() = runTest {
        val deviceId = "mockedDeviceId"
        val successResponse = ServiceResult<DefaultResponse>(ServiceResponse(ResponseType.SUCCESS))

        Mockito.`when`(deviceInfo.deviceId()).thenReturn(deviceId)
        Mockito.`when`(
            userRepository.registerUser(
                "John",
                "Male",
                "1990-01-01",
                "password",
                "test@example.com",
                deviceId
            )
        )
            .thenReturn(successResponse)

        viewModel.registerUser("John", "Male", "1990-01-01", "password", "test@example.com")

        Mockito.verify(deviceInfo).deviceId()
    }

    @Test
    fun `registerUser should call error massage on error`() = runTest {
        val deviceId = "mockedDeviceId"
        val errorResponse = ServiceResult<DefaultResponse>(
            ServiceResponse(
                ResponseType.ERROR,
                "",
                "Something went wrong"
            )
        )

        Mockito.`when`(deviceInfo.deviceId()).thenReturn(deviceId)
        Mockito.`when`(
            userRepository.registerUser(
                "John",
                "Male",
                "1990-01-01",
                "password",
                "test@example.com",
                deviceId
            )
        )
            .thenReturn(errorResponse)

        viewModel.errorMessage.test {
            viewModel.registerUser("John", "Male", "1990-01-01", "password", "test@example.com")

            val errorMessage = awaitItem()

            assertEquals(errorMessage, "Something went wrong")
        }
    }
}

