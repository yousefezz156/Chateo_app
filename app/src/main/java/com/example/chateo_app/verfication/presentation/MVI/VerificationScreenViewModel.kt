package com.example.chateo_app.verfication.presentation.MVI

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chateo_app.verfication.data.GetNumCode
import com.example.chateo_app.verfication.data.VerficationRepository
import com.example.chateo_app.verfication.domain.VerificationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VerificationScreenViewModel(val verificationScreenRepo: VerficationRepository, val verificationUseCase: VerificationUseCase, val getNumCode: GetNumCode): ViewModel() {

    private val _state= MutableStateFlow(VerficationScreenState())
    val state = _state.asStateFlow()

    init {
        onEvent(VerficationScreenIntent.LoadNumCodeList)
        onEvent(VerficationScreenIntent.DefaultNumCode)
    }

     fun onEvent(events: VerficationScreenIntent){
        when(events){
            is VerficationScreenIntent.SendNumber -> {
                sendNumber(events.number)
            }
            is VerficationScreenIntent.LoadNumCodeList -> getNumCodeList()

            is VerficationScreenIntent.DefaultNumCode -> getDefaultNumCode()
        }
    }

    fun getDefaultNumCode(){
        viewModelScope.launch {
            _state.value= _state.value.copy(defaultCode = getNumCode.getNumCodeByNetwork())

        }
    }

    fun getNumCodeList(){
        _state.value=_state.value.copy(listOfNum = verificationUseCase.numList())
    }
      fun sendNumber(number: String){
          viewModelScope.launch {
             // val response = verificationScreenRepo.sendNumber(number)
              _state.value = _state.value.copy(true)
          }
    }
}