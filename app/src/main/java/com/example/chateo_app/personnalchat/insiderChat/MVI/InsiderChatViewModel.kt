package com.example.chateo_app.personnalchat.insiderChat.MVI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chateo_app.personnalchat.data.ChateoDatabase
import com.example.chateo_app.personnalchat.domain.PersonnalChatRepo
import com.example.chateo_app.personnalchat.domain.entites.MessageDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class InsiderChatViewModel(val database: ChateoDatabase, val repo: PersonnalChatRepo = PersonnalChatRepo(database)) : ViewModel() {



    val _state = MutableStateFlow(InsiderChatState())
    var state = _state.asStateFlow()


    fun onEvent(event: InsiderChatIntent){
        when(event){
            is InsiderChatIntent.SendTextMessage -> sendMessage()
            else -> {}
        }
    }

    fun sendMessage( message:  MessageDetails){
        viewModelScope.launch {
            // launch for db
            val insertMessage= repo.insertMessage(message)

            //async for network


        }
    }


}
//// Enhanced ViewModel with better synchronization
//class InsiderChatViewModel : ViewModel() {
//    var currentAudioMessage by mutableStateOf<String?>(null)
//
//    private val _progress = MutableStateFlow(0f)
//    val progress: StateFlow<Float> = _progress.asStateFlow()
//
//    var player: MediaPlayer? = null
//
//    private val _messages = MutableStateFlow<List<Message>>(emptyList())
//    val messages = _messages.asStateFlow()
//
//    private val _storeAmp = MutableStateFlow<Map<String, List<Int>>>(emptyMap())
//    val storeAmp: StateFlow<Map<String, List<Int>>> = _storeAmp.asStateFlow()
//
//    fun sendAudio(messageId: Message) {
//        _messages.value = _messages.value + messageId
//    }
//
//    fun getAmp(messageId: String, file: String) {
//        viewModelScope.launch {
//            val ex: List<Int> = extractAudioData(file)  // Now gives real amplitudes
//            val updateList = _storeAmp.value.toMutableMap()
//            updateList[messageId] = ex
//            _storeAmp.value = updateList
//        }
//    }
//
//    fun seekTo(percent: Float) {
//        player?.let { mediaPlayer ->
//            val seekPosition = (mediaPlayer.duration * percent).toInt()
//            mediaPlayer.seekTo(seekPosition)
//        }
//    }
//
//    fun playAudio(messageId: String, context: Context, uri: String) {
//        // If playing same message → stop
//        if (player?.isPlaying == true && currentAudioMessage == messageId) {
//            stopAudio()
//            return
//        }
//
//        // Stop any previous audio before starting new one
//        stopAudio()
//
//        currentAudioMessage = messageId  // ✅ IMPORTANT: Set current message before loop
//
//        player = MediaPlayer().apply {
//            setDataSource(context, uri.toUri())
//            prepare()
//            start()
//            setOnCompletionListener {
//                stopAudio()
//            }
//        }
//
//        // Update progress every 100ms while playing
//        viewModelScope.launch {
//            while (player?.isPlaying == true && currentAudioMessage == messageId) {
//                val currentPos = player!!.currentPosition
//                val totalDuration = player!!.duration
//                _progress.value = (currentPos.toFloat() / totalDuration).coerceIn(0f, 1f)
//                delay(100)
//            }
//        }
//    }
//
//    fun transcribeAudioFile(filePath: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                // create client using your factory function
//                val client = httpRequest(OkHttp.create()) // or pass whichever engine you use
//                val baseUrl = "http://192.168.100.3:8000"
//
//                // call server
//                val result = postTranscription(client, baseUrl, filePath)
//
//                Log.d("TranscriptionResult", result.text)
//
//                // update message in UI list on main thread
//                withContext(Dispatchers.Main) {
//                    // replace message that has this voiceUrl with same message but text = result.text
//                    val updated = _messages.value.map { msg ->
//                        if (msg.voiceUrl == filePath) {
//                            msg.copy(text = result.text)
//                        } else msg
//                    }
//                    _messages.value = updated
//                    Log.d("messages", "${_messages.value}  ")
//                }
//
//                client.close()
//            } catch (e: Exception) {
//                e.printStackTrace()
//                // optionally surface an error state to the UI
//            }
//        }
//    }
//    fun stopAudio() {
//        player?.stop()
//        player?.release()
//        player = null
//        currentAudioMessage = null
//        _progress.value = 0f // Reset progress
//    }
//}