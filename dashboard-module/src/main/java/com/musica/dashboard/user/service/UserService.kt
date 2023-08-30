package com.musica.dashboard.user.service

import com.musica.common.service.volley.IService
import com.musica.common.service.volley.ServiceResult
import javax.inject.Inject


interface UserService {

    suspend fun userSignIn(userSignInRequest: UserSignInRequest): ServiceResult<SignInResponse>
}

const val USER_SIGN_IN_URL = "user/signin"

class UserServiceImpl @Inject constructor(
    private val service: IService
): UserService {
    override suspend fun userSignIn(userSignInRequest: UserSignInRequest): ServiceResult<SignInResponse> {
        return service.POST(USER_SIGN_IN_URL, userSignInRequest, SignInResponse::class.java)
    }

}