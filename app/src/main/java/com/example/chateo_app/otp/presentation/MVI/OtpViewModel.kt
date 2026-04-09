package com.example.chateo_app.otp.presentation.MVI

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chateo_app.otp.data.OtpRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OtpViewModel (val otpRepository: OtpRepository = OtpRepository()): ViewModel() {

    private var _state = MutableStateFlow(OtpState())
    var state= _state.asStateFlow()

    fun onEvent(events: OtpIntent){
        when (events){
            is OtpIntent.SendOtp -> sendOtp(events.otp)

            is OtpIntent.ResendOtp -> resendOtp()
        }
    }

    fun sendOtp(otp:Int){
        viewModelScope.launch{
            otpRepository.sendOtp(otp)
        }
    }

    fun resendOtp(){
        viewModelScope.launch {
            otpRepository.resendOtp()
            _state.value = _state.value.copy(resenOtp = true)
        }
    }
}