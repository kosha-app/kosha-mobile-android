package com.musica.common.settings.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.musica.common.device.repository.DeviceRepository
import com.musica.common.installs.DeviceInfo
import com.musica.common.service.models.response.ResponseType
import com.musica.common.user.repository.UserRepository
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap
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
class SettingsViewModel @Inject constructor(
    application: Application,
    private val deviceRepository: DeviceRepository,
    private val userRepository: UserRepository,
    private val deviceInfo: DeviceInfo
) : AndroidViewModel(application) {

    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableSharedFlow<String>()
    private val _logoutSuccessEvent = MutableSharedFlow<() -> Unit>()

    private val _name = MutableStateFlow("")

    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()
    val logoutSuccessEvent: SharedFlow<() -> Unit> = _logoutSuccessEvent.asSharedFlow()
    val name: StateFlow<String> = _name.asStateFlow()

    init {
        getProfileInfo()
    }

    fun logDeviceOut() {
        _isLoading.value = true
        viewModelScope.launch {
            val response = deviceInfo.deviceId()?.let { deviceRepository.logoutDevice(it) }

            if (response?.serviceResponse?.responseType == ResponseType.SUCCESS) {
                _logoutSuccessEvent.emit {}
            } else {
                response?.serviceResponse?.let { _errorMessage.emit(it.message) }
            }
            _isLoading.value = false
        }
    }

    private fun getProfileInfo() {
        viewModelScope.launch {
            val deviceResponse =
                deviceInfo.deviceId()?.let { deviceRepository.getCurrentDevice(it) }

            if (deviceResponse?.serviceResponse?.responseType == ResponseType.SUCCESS) {
                val userResponse = deviceResponse.data?.userId?.let {
                    userRepository.getUserProfile(
                        it
                    )
                }

                if (userResponse?.serviceResponse?.responseType == ResponseType.SUCCESS) {
                    _name.value = userResponse.data?.name.toString()
                }
            }
        }
    }

}