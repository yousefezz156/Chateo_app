package com.example.chateo_app.personnalchat.domain.entites

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

//data class Message (
//    val id: String,
//    val SenderID: String,
//    val text: String?,
//    val audioUrl: String?,
//    val voiceUrl: String?,
//    val mediaUri: HashMap<Uri,String?>?,
//    val document: Document?,
//    val location: Location?,
//    val contact: Contact?,
//    val timestamp: Long
//)

@Entity(tableName="message_table")
data class MessageDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val senderId:String?,
    val receiverId:String?,
    val text: String?,
    val audioUrl: String?,
    val voiceUrl: String?,
    val mediaUri: HashMap<Uri,String?>?,
    val document: Document?,
    val location: Location?,
    val contact: Contact?,
    val timestamp: Long
)







// --- Location Messages ---
data class Location(
    val latitude: Double,
    val longitude: Double
)





data class Document(
    val name : String,
    val size : String,
    val type : String,
    val url : String
)

// Contact data class
data class Contact(
    val name: String,
    val phoneNumber: String,
    val profileImageUrl: String? // Optional profile picture
)


