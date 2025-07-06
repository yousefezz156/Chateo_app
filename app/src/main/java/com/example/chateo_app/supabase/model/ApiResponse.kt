package com.example.chateo_app.supabase.model

sealed class ApiResponse<out T> {
    data class Successful<out T>(val message : String): ApiResponse<T>()
    data class Failed <out T>(val message : String) : ApiResponse<T>()
    object Loading : ApiResponse<Nothing>()
}