package com.example.chateo_app.personnalchat.domain

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun httpRequest(engine : HttpClientEngine): HttpClient{
    return HttpClient(engine){
        install(ContentNegotiation){
            json(
                Json{
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Logging) {
            level = LogLevel.BODY
        }


        install(DefaultRequest) {
            headers.append("x-api-key", "reqres-free-v1")
        }
    }
}