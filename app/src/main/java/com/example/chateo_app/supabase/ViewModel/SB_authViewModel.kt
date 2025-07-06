package com.example.chateo_app.supabase.ViewModel

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chateo_app.supabase.model.ApiResponse
import com.example.chateo_app.supabase.networkClient.SupabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.supabaseJson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SB_authViewModel :ViewModel() {

    val auth = SupabaseClient.client.auth
    var formattedPhoneNumber=""
    private val _authresponse = MutableStateFlow<ApiResponse<Unit>>(ApiResponse.Loading)
    val authresponse : StateFlow<ApiResponse<Unit>> = _authresponse.asStateFlow()

    private val _navigateToOtp = MutableStateFlow(false)
    val navigateToOtp : StateFlow<Boolean> = _navigateToOtp.asStateFlow()

    private val _verifyOtpSuccessfully = MutableStateFlow(false)
    val verifyOtpSuccessfully : StateFlow<Boolean> = _verifyOtpSuccessfully.asStateFlow()

     fun sendPhoneNumber(phoneNumber : String, activity: Activity){
        viewModelScope.launch {
            _authresponse.value = ApiResponse.Loading
            try {
                formattedPhoneNumber = if (phoneNumber.startsWith("+")) phoneNumber else "+$phoneNumber"
                auth.signInWith(OTP){
                    phone=formattedPhoneNumber
                }
                _authresponse.value = ApiResponse.Successful("Successfully")
                _navigateToOtp.value = true
            }catch (e: Exception){
                Toast.makeText(activity , "${formattedPhoneNumber}${e.message}" , Toast.LENGTH_LONG).show()
            }
        }
    }

     fun verifyOtp(otp : String, phoneNumber: String, activity: Activity){
        viewModelScope.launch {
            _authresponse.value = ApiResponse.Loading
            try {
               auth.verifyPhoneOtp(type = OtpType.Phone.SMS , phone = phoneNumber , token = otp )
                _authresponse.value=ApiResponse.Successful("successfully")
                _verifyOtpSuccessfully.value=true;
            }catch(e: Exception){
                Toast.makeText(activity, "${e.message}" , Toast.LENGTH_LONG).show()
            }
        }
    }

    fun navRest(){
        _navigateToOtp.value=false
    }
    fun verifyOtpBool(){
        _verifyOtpSuccessfully.value=false
    }
}