package com.example.chateo_app.verfication.presentation.MVI

sealed class VerficationScreenIntent {

    data class SendNumber(val number: String): VerficationScreenIntent()
//    data class selectedCountry(val country:String): VerficationScreenIntent()
    object LoadNumCodeList: VerficationScreenIntent()
    object DefaultNumCode: VerficationScreenIntent()

}