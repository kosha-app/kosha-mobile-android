package com.musica.dashboard.user.signin.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.provider.Settings
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.musica.common.device.DeviceInfo
import com.musica.common.service.models.response.ResponseType
import com.musica.dashboard.home.DashboardActivity
import com.musica.dashboard.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val application: Application,
    private val userRepository: UserRepository
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
            val response = userRepository.userSignIn(username = username, password, DeviceInfo.deviceId(application))

            if (response.serviceResponse.responseType == ResponseType.SUCCESS){
                _isSuccessful.emit(Intent(application, DashboardActivity::class.java))
            }else {
                _errorMessage.emit(response.data?.message.toString())
            }
            _isLoading.value = false
        }
    }

}