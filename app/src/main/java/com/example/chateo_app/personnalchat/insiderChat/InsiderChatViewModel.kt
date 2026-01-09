package com.example.chateo_app.personnalchat.insiderChat

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chateo_app.personnalchat.data.entites.Message
import com.example.chateo_app.personnalchat.domain.httpRequest
import com.example.chateo_app.personnalchat.domain.postTranscription
import extractAudioData
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.sqrt

// Enhanced ViewModel with better synchronization
class InsiderChatViewModel : ViewModel() {
    var currentAudioMessage by mutableStateOf<String?>(null)

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    var player: MediaPlayer? = null

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    private val _storeAmp = MutableStateFlow<Map<String, List<Int>>>(emptyMap())
    val storeAmp: StateFlow<Map<String, List<Int>>> = _storeAmp.asStateFlow()

    fun sendAudio(messageId: Message) {
        _messages.value = _messages.value + messageId
    }

    fun getAmp(messageId: String, file: String) {
        viewModelScope.launch {
            val ex: List<Int> = extractAudioData(file)  // Now gives real amplitudes
            val updateList = _storeAmp.value.toMutableMap()
            updateList[messageId] = ex
            _storeAmp.value = updateList
        }
    }

    fun seekTo(percent: Float) {
        player?.let { mediaPlayer ->
            val seekPosition = (mediaPlayer.duration * percent).toInt()
            mediaPlayer.seekTo(seekPosition)
        }
    }

    fun playAudio(messageId: String, context: Context, uri: String) {
        // If playing same message → stop
        if (player?.isPlaying == true && currentAudioMessage == messageId) {
            stopAudio()
            return
        }

        // Stop any previous audio before starting new one
        stopAudio()

        currentAudioMessage = messageId  // ✅ IMPORTANT: Set current message before loop

        player = MediaPlayer().apply {
            setDataSource(context, uri.toUri())
            prepare()
            start()
            setOnCompletionListener {
                stopAudio()
            }
        }

        // Update progress every 100ms while playing
        viewModelScope.launch {
            while (player?.isPlaying == true && currentAudioMessage == messageId) {
                val currentPos = player!!.currentPosition
                val totalDuration = player!!.duration
                _progress.value = (currentPos.toFloat() / totalDuration).coerceIn(0f, 1f)
                delay(100)
            }
        }
    }

    fun transcribeAudioFile(filePath: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // create client using your factory function
                val client = httpRequest(OkHttp.create()) // or pass whichever engine you use
                val baseUrl = "http://192.168.100.3:8000"

                // call server
                val result = postTranscription(client, baseUrl, filePath)

                Log.d("TranscriptionResult", result.text)

                // update message in UI list on main thread
                withContext(Dispatchers.Main) {
                    // replace message that has this voiceUrl with same message but text = result.text
                    val updated = _messages.value.map { msg ->
                        if (msg.voiceUrl == filePath) {
                            msg.copy(text = result.text)
                        } else msg
                    }
                    _messages.value = updated
                    Log.d("messages", "${_messages.value}  ")
                }

                client.close()
            } catch (e: Exception) {
                e.printStackTrace()
                // optionally surface an error state to the UI
            }
        }
    }
    fun stopAudio() {
        player?.stop()
        player?.release()
        player = null
        currentAudioMessage = null
        _progress.value = 0f // Reset progress
    }
}

//class InsiderChatViewModel() : ViewModel() {
//    var currentAudioMessage by  mutableStateOf<String?>(null)
//
//    var player : MediaPlayer? = null
//    var _messages = MutableStateFlow<List<Message>>(emptyList())
//    var messages  =_messages.asStateFlow()
//
//    private val _progress = MutableStateFlow(0f)
//    val progress: StateFlow<Float> = _progress.asStateFlow()
//
//    fun sendAudio(messageId: Message){
//        _messages.value = _messages.value + messageId
//    }
//
//    private var _storeAmp = MutableStateFlow<Map<String , List<Int>>>(emptyMap())
//    var storeAmp :StateFlow<Map<String , List<Int>>> = _storeAmp
//
//    // Store audio durations for proper waveform sizing
//    private var _audioDurations = MutableStateFlow<Map<String, Long>>(emptyMap())
//    val audioDurations: StateFlow<Map<String, Long>> = _audioDurations.asStateFlow()
//
//    // we cannot assign the map/list directly using stateflow or live data even if it's mutablestateflow because what inside the mutablestateflow is not mutable
//    fun getAmp(messageId: String,file:String){
//         viewModelScope.launch {
//             val audioData = extractAudioDataWithDuration(file)
//             android.util.Log.d(
//                 "InsiderChatViewModel",
//                 "Audio data for $messageId: ${audioData.amplitudes.size} points, ${audioData.durationMs}ms duration"
//             )
//             android.util.Log.d(
//                 "InsiderChatViewModel",
//                 "First 10 amplitudes: ${audioData.amplitudes.take(10)}"
//             )
//
//             // Don't over-compress the data - keep more points for accuracy
//             val targetSize = when {
//                 audioData.amplitudes.size <= 60 -> audioData.amplitudes.size   // Keep original if small
//                 audioData.amplitudes.size <= 150 -> 100       // Light compression
//                 else -> 120                // Still keep many points for accuracy
//             }
//             val finalAmplitudes = if (audioData.amplitudes.size > targetSize) {
//                 downsampleAmplitudes(audioData.amplitudes, targetSize)
//             } else {
//                 audioData.amplitudes
//             }
//
//             android.util.Log.d(
//                 "InsiderChatViewModel",
//                 "Final amplitudes: ${finalAmplitudes.size} points"
//             )
//             android.util.Log.d(
//                 "InsiderChatViewModel",
//                 "Final first 10: ${finalAmplitudes.take(10)}"
//             )
//
//             // Store both amplitudes and duration
//             val updateAmpList = _storeAmp.value.toMutableMap()
//             updateAmpList[messageId] = finalAmplitudes
//             _storeAmp.value = updateAmpList
//
//             val updateDurationList = _audioDurations.value.toMutableMap()
//             updateDurationList[messageId] = audioData.durationMs
//             _audioDurations.value = updateDurationList
//        }
//
//    }
//
//    private fun downsampleAmplitudes(amplitudes: List<Int>, targetSize: Int): List<Int> {
//        if (amplitudes.isEmpty()) return emptyList()
//        if (amplitudes.size <= targetSize) return amplitudes
//
//        val chunkSize = amplitudes.size / targetSize
//        return (0 until targetSize).map { i ->
//            val start = i * chunkSize
//            val end = minOf((i + 1) * chunkSize, amplitudes.size)
//            amplitudes.subList(start, end).maxOrNull() ?: 0
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
//    fun playAudio(messageId : String, context : Context, uri : String ){
//        // If the same message is playing, stop it
//        if (currentAudioMessage == messageId && player?.isPlaying == true) {
//            stopAudio()
//            return
//        }
//
//        // If a different message is playing, stop it first
//        if (player?.isPlaying == true) {
//            stopAudio()
//        }
//
//        currentAudioMessage = messageId
//        player = MediaPlayer().apply {
//            setDataSource(context, uri.toUri())
//            prepare()
//
//            // Update duration with actual MediaPlayer duration for consistency
//            val actualDuration = duration.toLong()
//            val updateDurationList = _audioDurations.value.toMutableMap()
//            updateDurationList[messageId] = actualDuration
//            _audioDurations.value = updateDurationList
//
//            android.util.Log.d(
//                "InsiderChatViewModel",
//                "MediaPlayer prepared: ${actualDuration}ms duration"
//            )
//
//            start()
//            setOnCompletionListener {
//                stopAudio()
//            }
//        }
//
//        viewModelScope.launch {
//            while (player != null && player!!.isPlaying && currentAudioMessage == messageId) {
//                val currentPos = player!!.currentPosition
//                val totalDuration = player!!.duration
//                val p = (currentPos.toFloat() / totalDuration).coerceIn(0f, 1f)
//                _progress.value = p
//
//                // Debug logging every 500ms to track timing
//                if (currentPos % 500 < 100) { // Log approximately every 500ms
//                    android.util.Log.d(
//                        "InsiderChatViewModel",
//                        "Playback: ${currentPos}ms / ${totalDuration}ms (${(p * 100).toInt()}%)"
//                    )
//                }
//
//                delay(100) // every 100ms
//            }
//
//            // If we exited the loop because audio stopped naturally, reset progress
//            if (currentAudioMessage == messageId && player != null && !player!!.isPlaying) {
//                _progress.value = 0f
//            }
//        }
//    }
//
//    fun stopAudio(){
//        player?.stop()
//        player?.release()
//        player = null
//        currentAudioMessage = null
//        _progress.value = 0f // Reset progress when audio stops
//    }
//}