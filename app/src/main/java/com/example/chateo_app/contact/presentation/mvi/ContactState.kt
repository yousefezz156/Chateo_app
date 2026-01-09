package com.example.chateo_app.contact.presentation.mvi

import com.example.chateo_app.contact.mockData.MockDataContact

data class ContactState(
    var mockContacts: List<MockDataContact> = emptyList(),
    val searchQuery: String = "",
    val addContact: Boolean = false
)
