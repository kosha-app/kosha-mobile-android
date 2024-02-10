package com.musica.common.service.models.response

data class SignInResponse(val username: String, val message: String)

data class GetUserInfoResponse(
    val name: String? = null,
    val email: String? = null,
    val dateOfBirth: String? = null,
    val gender: String? = null,
    val cellNumber: String? = null,
    val message: String? = null
)

data class CheckEmailResponse(val id: String?, val message: String)
