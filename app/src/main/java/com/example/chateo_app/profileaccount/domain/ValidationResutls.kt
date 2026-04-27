package com.example.chateo_app.profileaccount.domain

sealed class ValidationResutls {
    object Succes : ValidationResutls()
    data class Failed(val error:String): ValidationResutls()
}