package com.example.chateo_app.personnalchat.domain.entites

import android.net.Uri

data class MessagesUi(
    val senderId:String?,
    val receiverId:String?,
    val text: String?,
    val audioUrl: String?,
    val voiceUrl: String?,
    val mediaUri: HashMap<Uri,String?>?,
    val document: DocumentUi?,
    val location: LocationUi?,
    val contact: ContactUi?,
    val timestamp: Long
)

data class LocationUi(
    val latitude: Double,
    val longitude: Double
)





data class DocumentUi(
    val name : String,
    val size : String,
    val type : String,
    val url : String
)

// Contact data class
data class ContactUi(
    val name: String,
    val phoneNumber: String,
    val profileImageUrl: String? // Optional profile picture
)

