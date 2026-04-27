package com.example.chateo_app.profileaccount.domain

import kotlinx.serialization.SerialInfo
import kotlinx.serialization.Serializable

data class ProfileInfo(
    val image:String?,
    val firstName:String,
    val lastName:String?
)