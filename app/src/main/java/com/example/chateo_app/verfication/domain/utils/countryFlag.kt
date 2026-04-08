package com.example.chateo_app.verfication.domain.utils

fun countryFlag(country:String):String{
    return country
        .uppercase()
        .map { char ->
            Character.toChars(0x1F1E6 + char.code - 'A'.code)
        }
        .joinToString("") { String(it) }

}