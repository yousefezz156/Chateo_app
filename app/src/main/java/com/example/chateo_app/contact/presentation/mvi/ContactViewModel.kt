package com.example.chateo_app.contact.presentation.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.chateo_app.contact.mockData.ContactCard
import com.example.chateo_app.contact.mockData.MockDataContact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ContactViewModel: ViewModel(){
    private val _state = MutableStateFlow(ContactState())
    val state: StateFlow<ContactState> = _state.asStateFlow()

    init {
        events(ContactIntent.LoadContacts)

        Log.d("ContactViewModel", "ContactViewModel created!")
    }

    fun events(events:ContactIntent){
        when(events){
            is ContactIntent.AddContact -> {
                _state.value = _state.value.copy(
                    addContact = true
                )
            }
            is ContactIntent.LoadContacts -> {
                getMockContacts()
            }
            is ContactIntent.SearchContacts -> {

            }
        }
    }

    fun getMockContacts() {
        _state.value = _state.value.copy(mockContacts = ContactCard().getContactCard())
        Log.d("ContactViewModel", "ContactViewModel getMockContacts!")
        Log.d("ContactViewModel", _state.value.mockContacts.size.toString())
    }
}