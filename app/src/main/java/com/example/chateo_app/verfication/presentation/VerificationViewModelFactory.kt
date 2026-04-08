package com.example.chateo_app.verfication.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chateo_app.verfication.data.GetNumCode
import com.example.chateo_app.verfication.data.VerficationRepository
import com.example.chateo_app.verfication.domain.VerificationUseCase
import com.example.chateo_app.verfication.presentation.MVI.VerificationScreenViewModel
import io.ktor.client.HttpClient

class VerificationViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val deviceInfoDataSource = GetNumCode(context.applicationContext) // applicationContext to avoid leaks
        val getCountryCodesUseCase = VerificationUseCase()
        val httpClientRequest = HttpClient()
        val verificationRepo = VerficationRepository(httpClientRequest)


        return VerificationScreenViewModel(
            verificationRepo,
            getCountryCodesUseCase,
            deviceInfoDataSource
        ) as T
    }
}