package com.example.chateo_app.chat

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatViewModel : ViewModel() {
    private var _searchText = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()

    fun search(text :String){
        _searchText.value=text
    }
}