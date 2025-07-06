package com.example.chateo_app.chat.ChatCard

import com.example.chateo_app.R

class ChatCardList {

    fun getChatCard() : List<ChatCard>{
        val chat_card = mutableListOf<ChatCard>();

        chat_card.add(
            ChatCard("yousef" , "hello" , "today" , true , R.drawable.profile)
        )

        return chat_card
    }
}