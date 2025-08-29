//package com.example.chateo_app
//
//import android.app.Activity
//import android.widget.Toast
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.navigation.NavController
//import com.google.firebase.FirebaseException
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.PhoneAuthCredential
//import com.google.firebase.auth.PhoneAuthOptions
//import com.google.firebase.auth.PhoneAuthProvider
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import java.util.concurrent.TimeUnit
//
//class AuthViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
//
//    private val auth = FirebaseAuth.getInstance()
//    private val _navigateToOtp = MutableStateFlow(false)
//    val navigateToOtp: StateFlow<Boolean> = _navigateToOtp
//
//    private val _verificationID = MutableStateFlow(savedStateHandle["verificationID"] ?: "")
//    val verificationID: StateFlow<String?> = _verificationID
//
//    private val _isLoading = MutableStateFlow(false)
//    val isLoading: StateFlow<Boolean> = _isLoading
//
//    private val _otpVerificationSuccess = MutableStateFlow(false)
//    val otpVerificationSuccess: StateFlow<Boolean> = _otpVerificationSuccess
//
//    fun sendPhoneNumber(phoneNumber: String, activity: Activity) {
//        if (!isValidPhoneNumber(phoneNumber)) {
//            showToast(activity, "Invalid phone number!")
//            return
//        }
//
//        _isLoading.value = true
//
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNumber)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(activity)
//            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                    signInWithCredential(credential, activity)
//                }
//
//                override fun onVerificationFailed(e: FirebaseException) {
//                    _isLoading.value = false
//                    showToast(activity, "Verification Failed: ${e.message}")
//                }
//
//                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
//                    _isLoading.value = false
//                    _verificationID.value = verificationId
//                    savedStateHandle["verificationID"] = verificationId
//                    _navigateToOtp.value = true // Trigger navigation
//                    println("✅ onCodeSent() called. Verification ID: $verificationId")
//                    showToast(activity, "OTP Sent Successfully!")
//                }
//            })
//            .build()
//
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
//
//    fun verifyOTP(otp: String, activity: Activity) {
//        val storedVerificationId = _verificationID.value.takeIf { it.isNotEmpty() }
//            ?: savedStateHandle["verificationID"]
//
//        println("⚠️ Debug: Stored Verification ID before checking: $storedVerificationId")
//
//        if (storedVerificationId.isNullOrEmpty()) {
//            showToast(activity, "Verification ID is null, please retry")
//            return
//        }
//
//        val credential = PhoneAuthProvider.getCredential(storedVerificationId, otp)
//        signInWithCredential(credential, activity)
//    }
//
//    private fun signInWithCredential(credential: PhoneAuthCredential, activity: Activity) {
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    _otpVerificationSuccess.value = true // Trigger success state
//                    showToast(activity, "Login Successful!")
//                } else {
//                    showToast(activity, "Invalid OTP! Please try again.")
//                }
//            }
//    }
//
//    fun isValidPhoneNumber(phone: String): Boolean {
//        val regex = Regex("^\\+[1-9][0-9]{7,14}$") // Basic international format validation
//        return phone.matches(regex)
//    }
//
//    private fun showToast(activity: Activity, message: String) {
//        viewModelScope.launch {
//            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    fun navReset() {
//        _navigateToOtp.value = false
//    }
//
//    fun resetOtpVerificationState() {
//        _otpVerificationSuccess.value = false
//    }
//}