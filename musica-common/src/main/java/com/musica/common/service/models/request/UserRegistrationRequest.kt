package com.musica.common.service.models.request

data class UserRegistrationRequest(val username: String,val name: String,val surname: String, val password: String, val email: String)