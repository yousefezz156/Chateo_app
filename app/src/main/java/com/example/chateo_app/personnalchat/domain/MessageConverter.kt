package com.example.chateo_app.personnalchat.domain

import io.ktor.http.ContentType.Application.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MessageConverter {

    val json = Json{ignoreUnknownKeys = true}

    fun fromMapToString(map:HashMap<String, String?>?) : String? {
        return map.let { json.encodeToString(it) }
    }

    fun fromStringToMap(string: String?) : HashMap<String, String?>? {
        return string?.let { json.decodeFromString<HashMap<String, String?>?>(it) }
    }

}