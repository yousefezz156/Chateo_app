package com.example.chateo_app.verfication.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post

class VerficationRepository(val client: HttpClient) {

    suspend fun sendNumber(number:String){
        return client.post("sendNumber"){
            parameter("number" , number)
        }.body()
    }
}