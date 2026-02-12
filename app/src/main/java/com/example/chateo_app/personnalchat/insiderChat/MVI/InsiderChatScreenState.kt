package com.example.chateo_app.personnalchat.insiderChat.MVI

import com.example.chateo_app.personnalchat.domain.entites.Contact
import com.example.chateo_app.personnalchat.domain.entites.Document
import com.example.chateo_app.personnalchat.domain.entites.Location

data class InsiderChatState(
    val message:String?=null,
    val media:List<String>?=null,
    val documents:List<Document>?=emptyList(),
    val location: Location?=null,
    val contact:List<Contact>?=emptyList(),
    val sentIsSuccessful:Boolean?=null,
    val error:String?=null
)
