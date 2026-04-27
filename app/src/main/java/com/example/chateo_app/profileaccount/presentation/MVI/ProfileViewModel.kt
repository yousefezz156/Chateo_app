package com.example.chateo_app.profileaccount.presentation.MVI

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.chateo_app.Navigations.AppRoutes
import com.example.chateo_app.profileaccount.domain.ProfileInfoUseCase
import com.example.chateo_app.profileaccount.domain.ValidationResutls
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel( val useCase: ProfileInfoUseCase= ProfileInfoUseCase()): ViewModel() {


 private val _state = MutableStateFlow(ProfileState())
 val state =_state.asStateFlow()

    val results = useCase.validateFirstName(_state.value.firstName)


    fun event(events: ProfileEvent){
        when (events){
            is ProfileEvent.FirstNameChanged ->{
                _state.update { it.copy(events.firstName) }
            }
            is ProfileEvent.LastNameChanged ->{
                _state.update { it.copy(lastName = events.lastName) }
            }
            is ProfileEvent.ImageSelected -> {
                _state.update { it.copy(image = events.image) }
            }
            is ProfileEvent.SaveButton ->{
                when(results){
                    is ValidationResutls.Failed -> {_state.update { it.copy(error = results.error)}}
                    is ValidationResutls.Succes -> saveButton()
                }
            }
        }
    }

    fun saveButton(){

    }
}