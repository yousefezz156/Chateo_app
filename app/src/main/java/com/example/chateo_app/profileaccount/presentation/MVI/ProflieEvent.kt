package com.example.chateo_app.profileaccount.presentation.MVI

import coil3.Uri

sealed class ProfileEvent {
    data class FirstNameChanged(val firstName : String): ProfileEvent()
    data class LastNameChanged(val lastName: String): ProfileEvent()
    data class ImageSelected(val image: Uri):ProfileEvent()
    object SaveButton: ProfileEvent()
}