package com.musica.dashboard.user.signin.viewmodel

import android.app.Application
import app.cash.turbine.test
import com.musica.common.installs.DeviceInfo
import com.musica.common.service.models.response.ResponseType
import com.musica.common.service.models.response.*
import com.musica.common.service.volley.ServiceResult
import com.musica.common.user.repository.UserRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SignInViewModelTest {
    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var deviceInfo: DeviceInfo

    @InjectMocks
    private lateinit var viewModel: SignInViewModel

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
    fun `signin should emit success navigation event on successful response`() = runTest {
        val email = "test@example.com"
        val successResponse = ServiceResult(
            ServiceResponse(ResponseType.SUCCESS),
            SignInResponse("test@example.com", "")
        )

        whenever(userRepository.userSignIn(email, "password", "device-id")).thenReturn(
            successResponse
        )
        whenever(deviceInfo.deviceId()).thenReturn("device-id")

        viewModel.isSuccessful.test {
            viewModel.userSignIn(email, "password")

            val intent = awaitItem()

            Assert.assertNotNull(intent)
        }
    }

    @Test
    fun `signin should emit error message event on unsuccessful response`() = runTest {
        val email = "test@example.com"
        val errorResponse = ServiceResult<SignInResponse>(
            ServiceResponse(ResponseType.ERROR, "", "Something went wrong")
        )

        whenever(
            userRepository.userSignIn(
                email,
                "password",
                "device-id"
            )
        ).thenReturn(errorResponse)
        whenever(deviceInfo.deviceId()).thenReturn("device-id")

        viewModel.errorMessage.test {
            viewModel.userSignIn(email, "password")

            val errorMessage = awaitItem()

            Assert.assertEquals(errorMessage, "Something went wrong")
        }
    }
}