package com.example.chateo_app.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.net.URL

object Ktor {
    fun setupHttpClient(): HttpClient{
        return HttpClient(CIO) {

            install( ContentNegotiation){
                json(
                    Json {
                        ignoreUnknownKeys=true
                        isLenient=true
                    }
                )
            }

            install(Logging){
                level= LogLevel.ALL
                logger= Logger.DEFAULT
            }

            install(DefaultRequest){
                URL("http://192.168.1.104:8080/")
                contentType(ContentType.Application.Json)
            }

        }
    }
}