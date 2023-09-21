package com.musica.common.user

class SignInResponse(val username: String, val message: String)

class UserSignInRequest(val email: String, val password: String, val deviceId: String)

class UserRegistrationRequest(
    val name: String,
    val password: String,
    val email: String,
    val dateOfBirth: String,
    val gender: String,
    val deviceId: String,
)

class DeviceRequest(
    val deviceId: String,
    val isLoggedIn: Boolean
)

class CheckEmailResponse(val id: String?, val message: String)

class UserVerificationRequest(
    val otp: String
)

class GetUserInfoResponse(
    val name: String? = null,
    val email: String? = null,
    val dateOfBirth: String? = null,
    val gender: String? = null,
    val cellNumber: String? = null,
    val message: String? = null
)