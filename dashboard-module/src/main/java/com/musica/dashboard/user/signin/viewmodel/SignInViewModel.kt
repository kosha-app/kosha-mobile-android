package com.musica.dashboard.user.signin.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.musica.common.installs.DeviceInfo
import com.musica.common.service.models.response.ResponseType
import com.musica.common.user.repository.UserRepository
import com.musica.dashboard.DashboardActivity
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
class SignInViewModel @Inject constructor(
    private val application: Application,
    private val userRepository: UserRepository,
    private val deviceInfo: DeviceInfo
): AndroidViewModel(application) {

    private val _isLoading = MutableStateFlow(false)
    private val _isSuccessful = MutableSharedFlow<Intent>()
    private val _errorMessage = MutableSharedFlow<String>()

    val isLoading : StateFlow<Boolean> = _isLoading.asStateFlow()
    val isSuccessful: SharedFlow<Intent> = _isSuccessful.asSharedFlow()
    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()

    fun userSignIn(username: String, password: String){
        _isLoading.value = true
        viewModelScope.launch {
            val response = deviceInfo.deviceId()
                ?.let { userRepository.userSignIn(username = username, password, it) }

            if (response?.serviceResponse?.responseType == ResponseType.SUCCESS) {
                _isSuccessful.emit(Intent(application, DashboardActivity::class.java))
            } else {
                _errorMessage.emit(response?.serviceResponse?.message.toString())
            }
            _isLoading.value = false
        }
    }

}