package com.example.chateo_app.personnalchat.data.entites

import kotlinx.serialization.Serializable
import org.intellij.lang.annotations.Language

@Serializable
data class TranscriptResponse(
    val detected_language: String,
    val text: String
)
