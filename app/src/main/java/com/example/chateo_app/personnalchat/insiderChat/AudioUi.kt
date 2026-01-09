package com.example.chateo_app.personnalchat.insiderChat

import android.provider.CalendarContract
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.min


@Composable
fun WhatsAppAudioGraphic(
    amplitudes: List<Int>,
    progress: Float,
    onSeek: (Float) -> Unit,
    modifier: Modifier = Modifier,
    durationMs: Long = 5000L,
    barThickness: Dp = 3.dp,        // WhatsApp uses slightly thicker bars
    gap: Dp = 2.dp,                 // WhatsApp spacing
    minBarHeightRatio: Float = 0.2f, // Minimum bar height
    playedColor: Color = Color(0xFF00A884), // WhatsApp green
    unplayedColor: Color = Color(0xFFCCCCCC) // Light gray
) {
    // WhatsApp uses a more consistent width calculation
    val minWidth = 120.dp
    val maxWidth = 250.dp
    val calculatedWidth = when {
        durationMs <= 1000 -> minWidth // Very short audio gets min width
        durationMs <= 5000 -> (durationMs / 1000f * 30).dp // 30dp per second
        else -> maxWidth // Cap at max width for long audio
    }.coerceIn(minWidth, maxWidth)

    Canvas(
        modifier = modifier
            .width(calculatedWidth)
            .height(32.dp) // WhatsApp height
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    onSeek((offset.x / size.width).coerceIn(0f, 1f))
                }
            }
    ) {
        if (amplitudes.isEmpty()) return@Canvas

        val barWidthPx = barThickness.toPx()
        val gapWidthPx = gap.toPx()
        val totalBarWidth = barWidthPx + gapWidthPx
        val maxBars = (size.width / totalBarWidth).toInt()

        // Use all amplitude data but fit to available width
        val displayAmplitudes = if (amplitudes.size <= maxBars) {
            amplitudes
        } else {
            // Downsample to fit
            val step = amplitudes.size.toFloat() / maxBars
            (0 until maxBars).map { i ->
                val index = (i * step).toInt().coerceIn(0, amplitudes.size - 1)
                amplitudes[index]
            }
        }

        val centerY = size.height / 2f
        val minBarHeight = size.height * minBarHeightRatio
        val maxBarHeight = size.height * 0.9f

        // Calculate which bars are played
        val playedBars = (progress * displayAmplitudes.size).toInt()

        // Normalize amplitudes to available height
        val maxAmp = displayAmplitudes.maxOrNull() ?: 1
        val minAmp = displayAmplitudes.minOrNull() ?: 0
        val ampRange = maxAmp - minAmp

        amplitudes.forEachIndexed { i, amp ->

            Log.d("Amplitude", "Amplitude at index $i: $amp")
            // Normalize amplitude to bar height
            val normalizedAmp = if (ampRange > 0) {
                (amp - minAmp).toFloat() / ampRange
            } else {
                0.5f // Default to middle if no variation
            }

            val barHeight = minBarHeight + (maxBarHeight - minBarHeight) * normalizedAmp
            val x = i * totalBarWidth
            val color = if (i < playedBars) playedColor else unplayedColor

            // Draw rounded rectangle bar (WhatsApp style)
            drawRoundRect(
                color = color,
                topLeft = Offset(x, centerY - barHeight / 2f),
                size = Size(barWidthPx, barHeight),
                cornerRadius = CornerRadius(barWidthPx / 2f, barWidthPx / 2f)
            )
        }
    }
}
@Composable
fun AudioGraphic(
    amplitudes: List<Int>,
    progress: Float,                  // 0f = start, 1f = end
    onSeek: (Float) -> Unit,
    modifier: Modifier = Modifier,
    barThickness: Dp = 2.dp,           // bar width
    gap: Dp = 2.dp,                    // spacing between bars
    minBarHeightRatio: Float = 0.15f,  // smallest bar size
    playedColor: Color = Color(0xFF25D366), // WhatsApp green
    unplayedColor: Color = Color(0xFFB0B0B0)
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    // Calculate seek position based on tap
                    val percent = (offset.x / size.width).coerceIn(0f, 1f)
                    onSeek(percent)
                }
            }
    ) {
        if (amplitudes.isEmpty()) return@Canvas

        val barWidthPx = barThickness.toPx()
        val gapPx = gap.toPx()
        val centerY = size.height / 2
        val minH = size.height * minBarHeightRatio
        val maxH = size.height * 0.85f
        val playedBars = (progress * amplitudes.size).toInt()

        amplitudes.forEachIndexed { i, amp ->
            val normalizedAmp = (amp / 255f).coerceIn(0f, 1f)
            val h = minH + (maxH - minH) * normalizedAmp
            val x = i * (barWidthPx + gapPx)
            val color = if (i < playedBars) playedColor else unplayedColor

            drawRoundRect(
                color = color,
                topLeft = Offset(x, centerY - h / 2f),
                size = Size(barWidthPx, h),
                cornerRadius = CornerRadius(barWidthPx / 2f, barWidthPx / 2f)
            )
        }
    }
}


//@Composable
//fun AudioGraphic(
//    amplitudes : List<Int>,
//    progress : Float,
//    onSeek: (Float) -> Unit,
//    modifier: Modifier = Modifier) {
//
//    Canvas(modifier = modifier.fillMaxWidth().height(64.dp).pointerInput(Unit){ detectTapGestures { offset ->
//        val percent = offset.x/size.width
//        onSeek(percent)
//    }}) {
//        val barWidth = size.width/amplitudes.size
//
//        amplitudes.forEachIndexed { index, amplitude ->
//
//            val x = index * barWidth
//            val barHeight = (amplitude / 255f) * size.height
//
//            drawRoundRect(
//                color = if (index / amplitudes.size.toFloat() <= progress) Color.Green else Color.Gray,
//                topLeft = Offset(x, size.height / 2 - barHeight / 2),
//                size = Size(barWidth, barHeight),
//                cornerRadius = CornerRadius(barWidth / 2f, barWidth / 2f)
//
//
//            )
//
//        }
//    }
//
//}

//@Composable
//fun AudioGraphic(
//    amplitudes: List<Int>,
//    progress: Float,                 // 0f..1f
//    onSeek: (Float) -> Unit,
//    modifier: Modifier = Modifier,
//    barThickness: Dp = 8.dp,         // make bars bigger here
//    gap: Dp = 2.dp,                  // space between bars
//    minBarHeightRatio: Float = 0.18f,
//    playedColor: Color = Color(0xFF25D366), // WhatsApp green
//    unplayedColor: Color = Color(0xFFB0B0B0)
//) {
//    Canvas(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(88.dp) // taller so bars look beefy
//            .pointerInput(amplitudes.isEmpty()) {
//                if (amplitudes.isNotEmpty()) {
//                    detectTapGestures { offset ->
//                        onSeek((offset.x / size.width).coerceIn(0f, 1f))
//                    }
//                }
//            }
//    ) {
//        if (amplitudes.isEmpty()) return@Canvas
//
//        val bars = amplitudes.size
//        val step = size.width / bars                      // center-to-center distance per bar
//        val gapPx = gap.toPx().coerceAtMost(step * 0.6f)  // keep a visible gap
//        val desiredThickness = barThickness.toPx()
//        val thickness = min(desiredThickness, step - gapPx).coerceAtLeast(1f)
//
//        val centerY = size.height / 2f
//        val minH = size.height * minBarHeightRatio
//        val maxH = size.height
//
//        val playedBars = (progress.coerceIn(0f, 1f) * bars).toInt()
//
//        amplitudes.forEachIndexed { i, amp ->
//            val t = (amp.coerceIn(0, 255) / 255f)               // normalize
//            val h = minH + (maxH - minH) * t                    // ensure a minimum height
//            val cx = i * step + step / 2f                       // draw at center of each slot
//            val color = if (i < playedBars) playedColor else unplayedColor
//
//            drawRoundRect(
//                color = color,
//                topLeft = Offset(cx, centerY - h / 2f),
//                size = Size(thickness, h),
//                cornerRadius = CornerRadius(4.dp.toPx())
//            )
//        }
//    }
//}
