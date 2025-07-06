package com.example.chateo_app.chat.insiderChat

import android.net.Uri
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.StateFlow

data class Message (
    val id: String,
    val SenderID: String,
    val text: String?,
    val audioUrl: String?,
    val voiceUrl: String?,
    val mediaUri: HashMap<Uri,String?>?,
    val document: Document?,
    val location: Location?,
    val contact: Contact?,
    val timestamp: Long
)

data class MediaUri(
    val uri : Uri,
    val description : String? = null
)

data class Document(
    val name : String,
    val size : String,
    val type : String,
    val url : String

)

data class Location(
    val latitude: Double,
    val longitude: Double
)

// Contact data class
data class Contact(
    val name: String,
    val phoneNumber: String,
    val profileImageUrl: String? // Optional profile picture
)


