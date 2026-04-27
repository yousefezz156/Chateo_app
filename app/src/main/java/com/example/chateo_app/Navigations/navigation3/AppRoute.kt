package com.example.chateo_app.Navigations.navigation3

import androidx.navigation3.runtime.NavKey

@kotlinx.serialization.Serializable
sealed interface AppRoute: NavKey {

    @kotlinx.serialization.Serializable
    data object VerificationPhone : AppRoute, NavKey

    @kotlinx.serialization.Serializable
    data class Otp(val totalPhone: String) : AppRoute, NavKey

    @kotlinx.serialization.Serializable
    data object Profile : AppRoute, NavKey

    @kotlinx.serialization.Serializable
    data object MainContact : AppRoute, NavKey

    @kotlinx.serialization.Serializable
    data object MainChat : AppRoute, NavKey

    @kotlinx.serialization.Serializable
    data object TextChat : AppRoute, NavKey

    @kotlinx.serialization.Serializable
    data object Gallery : AppRoute, NavKey

    @kotlinx.serialization.Serializable
    data object Folder : AppRoute, NavKey

    @kotlinx.serialization.Serializable
    data object Audio : AppRoute, NavKey

    @kotlinx.serialization.Serializable
    data object ExternalContact : AppRoute, NavKey

    @kotlinx.serialization.Serializable
    data object Location : AppRoute, NavKey

    @kotlinx.serialization.Serializable
    data object Settings : AppRoute, NavKey
}