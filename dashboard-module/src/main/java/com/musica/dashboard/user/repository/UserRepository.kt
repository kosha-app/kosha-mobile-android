package com.musica.dashboard.user.repository

import com.musica.common.service.models.response.DefaultResponse
import com.musica.common.service.volley.ServiceResult
import com.musica.dashboard.user.service.DeviceRequest
import com.musica.dashboard.user.service.SignInResponse
import com.musica.dashboard.user.service.UserRegistrationRequest
import com.musica.dashboard.user.service.UserService
import com.musica.dashboard.user.service.UserSignInRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface UserRepository {
    suspend fun userSignIn(
        username: String,
        password: String,
        deviceId: String
    ): ServiceResult<SignInResponse>

    suspend fun registerUser(
        name: String,
        gender: String,
        dateOfBirth: String,
        password: String,
        email: String,
        deviceId: String
    ): ServiceResult<DefaultResponse>


    suspend fun resendOtp(username: String): ServiceResult<DefaultResponse>

    suspend fun checkEmail(email: String): ServiceResult<DefaultResponse>
}

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override suspend fun userSignIn(username: String, password: String, deviceId: String) =
        withContext(Dispatchers.IO) {
            userService.userSignIn(UserSignInRequest(username, password, deviceId))
        }

    override suspend fun registerUser(
        name: String,
        gender: String,
        dateOfBirth: String,
        password: String,
        email: String,
        deviceId: String
    ) = withContext(Dispatchers.IO) {
        userService.registerUser(
            UserRegistrationRequest(
                name,
                password,
                email,
                dateOfBirth,
                gender,
                deviceId
            )
        )
    }

    override suspend fun resendOtp(username: String) = withContext(Dispatchers.IO) {
        userService.resendOtp(username)
    }

    override suspend fun checkEmail(email: String) = withContext(Dispatchers.IO) {
        userService.checkEmail(email)
    }
}