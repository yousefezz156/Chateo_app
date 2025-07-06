package com.example.chateo_app.contact

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ContactViewModel : ViewModel(){
    private var _searchText = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()

fun onSearchChange(text:String){
    _searchText.value=text;
}


}