package com.example.chateo_app.otp.presentation.MVI

sealed class OtpIntent {
    data class SendOtp(val otp:Int): OtpIntent()
    object ResendOtp : OtpIntent()
}