package com.musica.dashboard.user.service

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