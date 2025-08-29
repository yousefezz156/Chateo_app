package com.example.chateo_app.contact.presentation.mvi

sealed class ContactIntent {
    object LoadContacts : ContactIntent()
    data class SearchContacts(val query: String) : ContactIntent()
    object AddContact : ContactIntent()
}