package com.musica.dashboard.user.register.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.musica.common.device.DeviceInfo
import com.musica.common.service.models.response.ResponseType
import com.musica.dashboard.home.DashboardActivity
import com.musica.dashboard.user.register.RegisterUserActivity
import com.musica.dashboard.user.repository.UserRepository
import com.musica.dashboard.user.signin.SignInActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterUserViewModel @Inject constructor(
    private val application: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(application) {

    private val _errorMessage = MutableSharedFlow<String>()
    private val _isLoading = MutableStateFlow(false)
    private val _resultRegisterSuccessIntent = MutableSharedFlow<Intent>()
    private val _emailCheckSuccessNav = MutableSharedFlow<String>()

    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val resultRegisterSuccessIntent: SharedFlow<Intent> = _resultRegisterSuccessIntent.asSharedFlow()
    val emailCheckSuccessNav: SharedFlow<String> = _emailCheckSuccessNav.asSharedFlow()

    fun checkEmail(email: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val response = userRepository.checkEmail(email)

            if (response.serviceResponse.responseType == ResponseType.SUCCESS) {
                _emailCheckSuccessNav.emit(RegisterUserActivity.OTP_CAPTURE_SCREEN)
            } else {
                _errorMessage.emit(response.data?.message.toString())
            }
            _isLoading.value = false
        }
    }

    fun registerUser(
        name: String,
        gender: String,
        dateOfBirth: String,
        password: String,
        email: String
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            val response =
                userRepository.registerUser(
                    name = name,
                    gender = gender,
                    dateOfBirth = dateOfBirth,
                    password = password,
                    email = email,
                    deviceId = DeviceInfo.deviceId(application)
                )
            if (response.serviceResponse.responseType == ResponseType.SUCCESS) {
                _resultRegisterSuccessIntent.emit(Intent(application, DashboardActivity::class.java))
            } else {
                _errorMessage.emit(response.data?.message.toString())
            }
            _isLoading.value = false
        }
    }
}