package com.example.chateo_app.otp.presentation.MVI

data class OtpState(
    val otp:Int=0,
    val resenOtp: Boolean = false,
    val otpError:String? =null
)
