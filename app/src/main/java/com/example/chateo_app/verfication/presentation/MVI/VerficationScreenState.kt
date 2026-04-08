package com.example.chateo_app.verfication.presentation.MVI

import com.example.chateo_app.verfication.domain.Numbers

data class VerficationScreenState(
    val numberSent:Boolean=false,
    val listOfNum: List<Numbers> = emptyList(),
    val defaultCode:String? =""
)
