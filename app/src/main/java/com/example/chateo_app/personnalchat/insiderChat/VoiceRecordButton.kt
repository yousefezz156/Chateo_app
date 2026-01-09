package com.example.chateo_app.personnalchat.insiderChat

import android.media.MediaRecorder
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import com.example.chateo_app.personnalchat.insiderChat.IconList.Icon
import kotlinx.coroutines.delay
import java.io.File

@Composable
fun VoiceRecorderBox(
    isRecording: Boolean,
    onRecordingStart: (File, MediaRecorder) -> Unit,
    onRecordingCancel: (MediaRecorder?, File?) -> Unit,
    onRecordingFinish: (MediaRecorder?, File?) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var recorder by remember { mutableStateOf<MediaRecorder?>(null) }
    var audioFile by remember { mutableStateOf<File?>(null) }
    var drag by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier.pointerInput(Unit) {
            detectDragGestures(
                onDragStart = {
                    val file = File(context.cacheDir, "recorder_audio.mp3")
                    audioFile = file
                    recorder = MediaRecorder().apply {
                        setAudioSource(MediaRecorder.AudioSource.MIC)
                        setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                        setOutputFile(file.absolutePath)
                        setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                        prepare()
                        start()
                    }
                    onRecordingStart(file, recorder!!)
                    drag = 0f
                },
                onDrag = { _, dragAmount -> drag += dragAmount.x },
                onDragEnd = {
                    if (drag < -100f) {
                        onRecordingCancel(recorder, audioFile)
                        audioFile?.delete()
                    } else {
                        onRecordingFinish(recorder, audioFile)
                    }
                    recorder = null
                    audioFile = null
                }
            )
        }
    ) {
        if (isRecording) {
            var voiceSeconds by remember { mutableStateOf(0) }
            var minutes by remember { mutableStateOf(0) }
            LaunchedEffect(isRecording) {
                while (isRecording) {
                    delay(1000)
                    voiceSeconds++
                    if (voiceSeconds == 60) {
                        minutes++
                        voiceSeconds = 0
                    }
                }
            }
            BottomAppBar {
                Text("Recording: $minutes:${voiceSeconds}s — swipe left to cancel")
            }
        } else {
            androidx.compose.material3.Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Start Recording"
            )
        }
    }
}
