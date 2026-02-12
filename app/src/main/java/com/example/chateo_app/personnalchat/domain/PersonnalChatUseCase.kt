package com.example.chateo_app.personnalchat.domain

import com.example.chateo_app.personnalchat.domain.entites.Contact
import com.example.chateo_app.personnalchat.domain.entites.ContactUi
import com.example.chateo_app.personnalchat.domain.entites.Document
import com.example.chateo_app.personnalchat.domain.entites.DocumentUi
import com.example.chateo_app.personnalchat.domain.entites.Location
import com.example.chateo_app.personnalchat.domain.entites.LocationUi
import com.example.chateo_app.personnalchat.domain.entites.MessageDetails
import com.example.chateo_app.personnalchat.domain.entites.MessagesUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class PersonnalChatUseCase(val repository: PersonnalChatRepo) {

      fun getPersonnalChat():Flow<List<MessagesUi>>{

         return repository.getPersonnalChats().map { i -> i.map { it.mapIt() } }
     }



    fun Document.toDocumentUi(): DocumentUi{
        return DocumentUi(
            name = name,
            size = size,
            type = type,
            url = url
        )
    }

    fun Location.toLocationUi(): LocationUi{
        return LocationUi(
            latitude = latitude,
            longitude = longitude,
        )
    }

    fun Contact.toContactUi(): ContactUi{
        return ContactUi(
            name = name,
            phoneNumber = phoneNumber,
            profileImageUrl = profileImageUrl
        )
    }

    fun MessageDetails.mapIt(): MessagesUi{
        return MessagesUi(
            senderId = senderId,
            receiverId = receiverId,
            text = text,
            audioUrl = audioUrl,
            voiceUrl = voiceUrl,
            mediaUri = mediaUri,
            document = document?.toDocumentUi(),
            location =location?.toLocationUi(),
            contact?.toContactUi(),
            timestamp = timestamp
        )

    }

}