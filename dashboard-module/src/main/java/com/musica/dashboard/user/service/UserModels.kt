package com.musica.dashboard.user.service

class SignInResponse(val username: String, val message: String)

class UserSignInRequest(val username: String, val password: String, val deviceId: String)