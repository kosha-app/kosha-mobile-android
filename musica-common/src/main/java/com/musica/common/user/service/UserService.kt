package com.musica.common.user.service

import com.musica.common.service.models.response.*
import com.musica.common.service.models.request.*
import com.musica.common.service.volley.IService
import com.musica.common.service.volley.ServiceResult
import javax.inject.Inject


interface UserService {

    suspend fun userSignIn(userSignInRequest: UserSignInRequest): ServiceResult<SignInResponse>

    suspend fun registerUser(userRegistrationRequest: UserRegistrationRequest): ServiceResult<DefaultResponse>

    suspend fun resendOtp(username: String): ServiceResult<DefaultResponse>

    suspend fun checkEmail(email: String): ServiceResult<CheckEmailResponse>

    suspend fun verifyOtp(
        id: String,
        request: UserVerificationRequest
    ): ServiceResult<Void>

    suspend fun getUserProfile(userId: String): ServiceResult<GetUserInfoResponse>
}

const val USER_SIGN_IN_URL = "user/signin"
const val REGISTER_USER_URL = "user/register"
const val RESEND_OTP_URL = "user/resendOtp/%s"
const val CHECK_EMAIL_URL = "user/checkemail/%s"
const val VERIFY_OTP_URL = "user/verification/%s"
const val GET_USER_PROFILE_URL = "user/profile/%s"

class UserServiceImpl @Inject constructor(
    private val service: IService
) : UserService {
    override suspend fun userSignIn(userSignInRequest: UserSignInRequest): ServiceResult<SignInResponse> {
        return service.POST(USER_SIGN_IN_URL, userSignInRequest, SignInResponse::class.java)
    }

    override suspend fun registerUser(userRegistrationRequest: UserRegistrationRequest): ServiceResult<DefaultResponse> {
        return service.POST(REGISTER_USER_URL, userRegistrationRequest, DefaultResponse::class.java)
    }

    override suspend fun resendOtp(username: String): ServiceResult<DefaultResponse> {
        return service.POST(RESEND_OTP_URL.format(username), DefaultResponse::class.java)
    }

    override suspend fun checkEmail(email: String): ServiceResult<CheckEmailResponse> {
        return service.POST(CHECK_EMAIL_URL.format(email), CheckEmailResponse::class.java)
    }

    override suspend fun verifyOtp(
        id: String,
        request: UserVerificationRequest
    ): ServiceResult<Void> {
        return service.POST(VERIFY_OTP_URL.format(id), request, Void::class.java)
    }

    override suspend fun getUserProfile(userId: String): ServiceResult<GetUserInfoResponse> {
        return service.GET(GET_USER_PROFILE_URL.format(userId), GetUserInfoResponse::class.java)
    }

}