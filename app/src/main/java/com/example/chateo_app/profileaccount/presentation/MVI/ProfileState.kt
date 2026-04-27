package com.example.chateo_app.profileaccount.presentation.MVI

import coil3.Uri

data class ProfileState(
    val firstName:String="",
    val lastName:String? = "",
    val image: Uri?=null,
    val error:String =""
)
