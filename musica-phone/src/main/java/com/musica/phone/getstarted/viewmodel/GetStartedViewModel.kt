package com.musica.phone.getstarted.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.provider.Settings.*
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.musica.common.device.repository.DeviceRepository
import com.musica.common.service.models.response.ResponseType
import com.musica.phone.getstarted.ErrorActivity
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


@SuppressLint("HardwareIds")
@HiltViewModel
class GetStartedViewModel @Inject constructor(
    application: Application,
    private val deviceRepository: DeviceRepository
): AndroidViewModel(application) {

    private val _isLoading = MutableStateFlow(false)
    private val _isLoggedIn = MutableStateFlow(false)
    private val _isConnectionError = MutableSharedFlow<Intent>()
    private val _errorMessage = MutableSharedFlow<String>()

    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
    val isConnectionError: SharedFlow<Intent> = _isConnectionError.asSharedFlow()
    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            val androidId = Secure.getString(application.contentResolver, Secure.ANDROID_ID) // to be passed to sign in screen
            val response = withContext(Dispatchers.IO){
                deviceRepository.checkDevice(androidId)
            }

//

            if (response.serviceResponse.responseType == ResponseType.SUCCESS){
                _isLoading.value = false
                _isLoggedIn.value = response.data?.loggedIn ?: false
                Log.e("Logged In", "Check : ${response.data?.loggedIn}")
            }else if (response.serviceResponse.responseType == ResponseType.CONNECTION_ERROR){
                _isConnectionError.emit(Intent(application.applicationContext, ErrorActivity::class.java))
            } else{
                _isLoading.value = false
                _errorMessage.emit(response.serviceResponse.code.toString())
            }
        }
    }

}