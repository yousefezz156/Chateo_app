package com.example.chateo_app.chat.insiderChat

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun AudioGraphic(
    amplitudes : List<Int>,
    progress : Float,
    onSeek: (Float) -> Unit,
    modifier: Modifier = Modifier) {
    
    Canvas(modifier = modifier.fillMaxWidth().height(64.dp).pointerInput(Unit){ detectTapGestures { offset ->
        val percent = offset.x/size.width
        onSeek(percent)
    }}) {
        val barWidth = size.width/amplitudes.size

        amplitudes.forEachIndexed { index, amplitude ->

            val x = index * barWidth
            val barHeight = (amplitude / 255f) * size.height

            drawLine(
                color = if (index / amplitudes.size.toFloat() <= progress) Color.Green else Color.Gray,
                start = Offset(x, size.height / 2 - barHeight / 2),
                end = Offset(x, size.height / 2 + barHeight / 2),
                strokeWidth = barWidth
            )

        }
    }

}