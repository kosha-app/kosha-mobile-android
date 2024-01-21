package com.musica.dashboard.user.register.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.musica.common.installs.DeviceInfo
import com.musica.common.service.models.response.ResponseType
import com.musica.common.user.repository.UserRepository
import com.musica.dashboard.DashboardActivity
import com.musica.dashboard.user.register.RegisterUserActivity
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
    private val userRepository: UserRepository,
    private val deviceInfo: DeviceInfo
) : AndroidViewModel(application) {

    private val _errorMessage = MutableSharedFlow<String>()
    private val _isLoading = MutableStateFlow(false)
    private var id = ""
    private val _resultRegisterSuccessIntent = MutableSharedFlow<Intent>()
    private val _emailCheckSuccessNav = MutableSharedFlow<String>()
    private val _verifyOtpSuccessNav = MutableSharedFlow<String>()

    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val resultRegisterSuccessIntent: SharedFlow<Intent> = _resultRegisterSuccessIntent.asSharedFlow()
    val emailCheckSuccessNav: SharedFlow<String> = _emailCheckSuccessNav.asSharedFlow()
    val verifyOtpSuccessNav: SharedFlow<String> = _verifyOtpSuccessNav.asSharedFlow()

    fun checkEmail(email: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val response = userRepository.checkEmail(email)

            if (response.serviceResponse.responseType == ResponseType.SUCCESS) {
                id = response.data?.id.toString()
                _emailCheckSuccessNav.emit(RegisterUserActivity.OTP_CAPTURE_SCREEN)
            } else {
                _errorMessage.emit(response.data?.message.toString())
            }
            _isLoading.value = false
        }
    }

    fun verifyOtp(otp: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val response = userRepository.verifyOtp(id, otp)
            if (response.serviceResponse.responseType == ResponseType.SUCCESS) {
                _verifyOtpSuccessNav.emit(RegisterUserActivity.PASSWORD_CAPTURE_SCREEN)
            } else {
                _errorMessage.emit(response.serviceResponse.message)
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
                deviceInfo.deviceId()?.let {
                    userRepository.registerUser(
                        name = name,
                        gender = gender,
                        dateOfBirth = dateOfBirth,
                        password = password,
                        email = email,
                        deviceId = it
                    )
                }
            if (response?.serviceResponse?.responseType == ResponseType.SUCCESS) {
                _resultRegisterSuccessIntent.emit(
                    Intent(
                        application,
                        DashboardActivity::class.java
                    )
                )
            } else {
                _errorMessage.emit(response?.data?.message.toString())
            }
            _isLoading.value = false
        }
    }
}