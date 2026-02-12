//package com.example.chateo_app.personnalchat.insiderChat
//
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