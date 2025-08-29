package com.example.chateo_app.personnalchat.insiderChat

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chateo_app.personnalchat.data.entites.Message
import extractAudioData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InsiderChatViewModel() : ViewModel() {
    var currentAudioMessage by  mutableStateOf<String?>(null)

    var player : MediaPlayer? = null
    var _messages = MutableStateFlow<List<Message>>(emptyList())
    var messages  =_messages.asStateFlow()

    fun sendAudio(messageId: Message){
        _messages.value = _messages.value + messageId
    }

    private var _storeAmp = MutableStateFlow<Map<String , List<Int>>>(emptyMap())
    var storeAmp :StateFlow<Map<String , List<Int>>> = _storeAmp


    // we cannot assign the map/list directly using stateflow or live data even if it's mutablestateflow because what inside the mutablestateflow is not mutable
    fun getAmp(messageId: String,file:String){
         viewModelScope.launch {
           var ex : List<Int> = extractAudioData(file)
             val updateList = _storeAmp.value.toMutableMap()
             updateList[messageId] = ex
             _storeAmp.value = updateList
        }

    }



    fun playAudio(messageId : String, context : Context, uri : String ){
        if(player?.isPlaying == true) {
            player?.stop()
            player?.release()
            player = null
            currentAudioMessage = null

            return
        }

            player = MediaPlayer().apply {
                setDataSource(context, uri.toUri())
                prepare()
                start()
                setOnCompletionListener {
                    stopAudio()
                }
            }



    }

    fun stopAudio(){
        player?.stop()
        player?.release()
        player = null
        currentAudioMessage = null
    }




}