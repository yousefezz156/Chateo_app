package com.example.chateo_app.contact.mockData

import androidx.annotation.DrawableRes

data class MockDataContact(
    @DrawableRes val image:Int,
    val firstName : String,
    val lastName: String,
    val showOnlineStatus: Boolean,
    val lastSeen: String = if (showOnlineStatus) "Online" else "Last seen recently",
)
