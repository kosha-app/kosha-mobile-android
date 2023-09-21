package com.musica.phone.getstarted.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.provider.Settings.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.musica.common.device.repository.DeviceRepository
import com.musica.common.installs.DeviceInfo
import com.musica.common.service.models.response.ResponseType
import com.musica.dashboard.home.DashboardActivity
import com.musica.phone.getstarted.ErrorActivity
import com.musica.phone.getstarted.LandingActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@SuppressLint("HardwareIds")
@HiltViewModel
class GetStartedViewModel @Inject constructor(
    application: Application,
    private val deviceRepository: DeviceRepository,
    private val deviceInfo: DeviceInfo
): AndroidViewModel(application) {

    private val _isLoading = MutableStateFlow(false)
    private val _returnIntent = MutableSharedFlow<Intent>()
    private val _errorMessage = MutableSharedFlow<String>()

    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val returnIntent: SharedFlow<Intent> = _returnIntent.asSharedFlow()
    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()

    init {
        _isLoading.value = true
        viewModelScope.launch {
            val response = deviceInfo.deviceId()?.let { deviceRepository.checkDevice(it) }

            when (response?.serviceResponse?.responseType) {
                ResponseType.SUCCESS -> {
                    _returnIntent.emit(
                        Intent(
                            application.applicationContext,
                            DashboardActivity::class.java
                        )
                    )
                }

                ResponseType.CONNECTION_ERROR -> {
                    _returnIntent.emit(
                        Intent(
                            application.applicationContext,
                            ErrorActivity::class.java
                        )
                    )
                }

                else -> {
                    if (response?.data?.loggedIn != null) {
                        _returnIntent.emit(
                            Intent(
                                application.applicationContext,
                                LandingActivity::class.java
                            )
                        )
                    } else {
                        _errorMessage.emit(response?.data?.message.toString())
                    }
                }
            }

            _isLoading.value = false
        }
    }

}