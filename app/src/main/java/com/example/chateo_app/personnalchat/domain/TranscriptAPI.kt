package com.example.chateo_app.personnalchat.domain

import android.util.Log
import com.example.chateo_app.personnalchat.domain.entites.TranscriptResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import java.io.File


suspend fun postTranscription(
    client: HttpClient,
    baseUrl: String,
    filePath: String
): TranscriptResponse {
    val file = File(filePath)
    require(file.exists()) { "Audio file not found: $filePath" }

    Log.d("postTranscription", "Starting POST to $baseUrl/transcribe with file=$filePath")

    val contentTypeString = when (file.extension.lowercase()) {
        "wav" -> "audio/wav"
        "mp3" -> "audio/mpeg"
        "m4a", "mp4", "aac" -> "audio/mp4"
        else -> "application/octet-stream"
    }

    val response: HttpResponse = client.post("$baseUrl/transcribe") {
        setBody(
            MultiPartFormDataContent(
                formData {
                    append(
                        "file",
                        file.readBytes(),
                        Headers.build {
                            append(
                                HttpHeaders.ContentDisposition,
                                "form-data; name=\"file\"; filename=\"${file.name}\""
                            )
                            append(HttpHeaders.ContentType, contentTypeString)
                        }
                    )
                }
            )
        )
    }

    Log.d("postTranscription", "Response status: ${response.status}")

    if (!response.status.isSuccess()) {
        val errorBody = response.bodyAsText()
        Log.e("postTranscription", "Error response: $errorBody")
        throw Exception("Transcription failed: ${response.status}")
    }


    return response.body()
}
