package com.example.chateo_app.profileaccount.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.Url

class ProfileInfoRepository(val client: HttpClient) {

    suspend fun sendProfileInfo(image: String, firstName:String, lastName:String){
        return client.post("profileInfo"){
            parameter("image", image)
            parameter("firstName", firstName)
            parameter("lastName", lastName)
        }.body()
    }
}