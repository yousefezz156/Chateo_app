package com.example.chateo_app.personnalchat.domain

import com.example.chateo_app.personnalchat.data.ChateoDatabase
import com.example.chateo_app.personnalchat.domain.entites.MessageDetails

class PersonnalChatRepo(val chateoDatabase: ChateoDatabase) {

    fun getPersonnalChats() = chateoDatabase.messageDao().getAllMessages()

    suspend fun insertMessage(message: MessageDetails) = chateoDatabase.messageDao().upsertMessage(message)
}