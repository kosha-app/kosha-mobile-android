package com.musica.common.service.models.request

data class UserSignInRequest(val email: String, val password: String, val deviceId: String)

data class UserRegistrationRequest(
    val name: String,
    val password: String,
    val email: String,
    val dateOfBirth: String,
    val gender: String,
    val deviceId: String,
)

data class UserVerificationRequest(
    val otp: String
)