package com.example.chateo_app.profileaccount.domain

class ProfileInfoUseCase {
    fun validateFirstName(firstName:String): ValidationResutls{
        if(firstName.isBlank())
            return ValidationResutls.Failed("please fill the name ")
        else
            return ValidationResutls.Succes
    }

}