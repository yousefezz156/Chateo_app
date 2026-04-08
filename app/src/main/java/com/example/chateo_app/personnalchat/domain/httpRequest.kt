package com.example.chateo_app.personnalchat.domain

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.SocketTimeoutException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.json.Json

fun httpRequest(engine : HttpClientEngine): HttpClient{


//    val client = HttpClient(CIO) {
//        engine{
//            endpoint{
//                connectTimeout=8_000
//            }
//        }
//
//        install(ContentNegotiation){
//            json(
//                Json {
//                    ignoreUnknownKeys=true;
//                }
//            )
//        }
//
//        install(Logging){
//            level= LogLevel.HEADERS
//        }
//
//        defaultRequest {
//            url(
//                "https://api.myapp.com/v1/"
//            )
//            contentType(ContentType.Application.Json)
//        }
//    }
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

        install(HttpTimeout){
            socketTimeoutMillis=120_000;// the transcripting is taking long time and the defualt socket timeout value is not enough
        }


//        install(DefaultRequest) {
//            headers.append("x-api-key", "reqres-free-v1")
//        }
    }

//    return client
}

