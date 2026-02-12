package com.example.chateo_app.personnalchat.insiderChat.MVI


sealed class InsiderChatIntent {
    data class SendTextMessage(val message: String, val senderId: String, val timestamp: Long) : InsiderChatIntent()
    data class MediaMessage(val mediaContent: Map<String, String?>) :InsiderChatIntent()
    data class LocationMessage( val latitude: Double, val longitude: Double) : InsiderChatIntent()
    data class DocumentMessage(val name : String, val size : String, val type : String, val url : String) : InsiderChatIntent()
    data class contactMessage(val name: String, val phoneNumber: String, val profileImageUrl: String?) : InsiderChatIntent()

    //message actions
    data class DeleteMessage(val messageId: String) : InsiderChatIntent()
    data class EditMessage(val messageId: String, val newMessage: String) : InsiderChatIntent()
    data class ForwardMessage(val messageId: String, val receiverId: String) : InsiderChatIntent()
    data class ReactToMessage(val messageId: String, val reaction: String) : InsiderChatIntent()

}








