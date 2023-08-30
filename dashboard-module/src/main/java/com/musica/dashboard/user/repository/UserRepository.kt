package com.musica.dashboard.user.repository

import com.musica.common.service.volley.ServiceResult
import com.musica.dashboard.user.service.SignInResponse
import com.musica.dashboard.user.service.UserService
import com.musica.dashboard.user.service.UserSignInRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface UserRepository {
    suspend fun userSignIn(username: String, password: String, deviceId: String): ServiceResult<SignInResponse>
}

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
): UserRepository {

    override suspend fun userSignIn(username: String, password: String, deviceId: String) = withContext(Dispatchers.IO) {
        userService.userSignIn(UserSignInRequest(username, password, deviceId))
    }

}