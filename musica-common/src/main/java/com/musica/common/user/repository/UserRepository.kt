package com.musica.common.user.repository

import com.musica.common.service.models.response.DefaultResponse
import com.musica.common.service.volley.ServiceResult
import com.musica.common.user.CheckEmailResponse
import com.musica.common.user.GetUserInfoResponse
import com.musica.common.user.SignInResponse
import com.musica.common.user.UserRegistrationRequest
import com.musica.common.user.UserSignInRequest
import com.musica.common.user.UserVerificationRequest
import com.musica.common.user.service.UserService
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

    suspend fun checkEmail(email: String): ServiceResult<CheckEmailResponse>

    suspend fun verifyOtp(id: String, otp: String): ServiceResult<DefaultResponse>

    suspend fun getUserProfile(userId: String): ServiceResult<GetUserInfoResponse>
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

    override suspend fun verifyOtp(id: String, otp: String) = withContext(Dispatchers.IO) {
        userService.verifyOtp(id, UserVerificationRequest(otp))
    }

    override suspend fun getUserProfile(userId: String) = withContext(Dispatchers.IO) {
        userService.getUserProfile(userId)
    }
}