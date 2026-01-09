import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMetadataRetriever
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.random.Random

suspend fun extractAudioData(file: String): List<Int> = withContext(Dispatchers.IO) {
    val amplitudes = mutableListOf<Int>()
    val extractor = MediaExtractor()
    extractor.setDataSource(file)


    // Select first audio track
    // when you debug this then write a comment that you had finished debugging
    val trackIndex = (0 until extractor.trackCount).firstOrNull { i ->
        /* so here we look for the MIME type for ex ("video/avc", "audio/mp4", "text/vtt)
        and if it starts with "audio/", then return TRUE, else it return false and keep looking*/
        extractor.getTrackFormat(i).getString(MediaFormat.KEY_MIME)?.startsWith("audio/") == true
    } ?: return@withContext emptyList()  // if the trackIndex is null then it returns and empty list to prevent the app from crashing

    /* after we find the right track index and we ensure that it's not null,
    then here we start focusing on this track by selecting this specific track from the extractor and then,
    we store it's format and it's mime type. We do this step to start extracting the the audio data
    and feed it to the decoder and also in order to generate the eave form
    we need this format and it's mime type */
    extractor.selectTrack(trackIndex)
    val format = extractor.getTrackFormat(trackIndex)
    val mime = format.getString(MediaFormat.KEY_MIME) ?: return@withContext emptyList()

    // Create decoder

    /*so now we have already encoded the audio in the function which is VocieRecorderBox in line 46,
     and we have done this part to decrease the size and remove the unecessry input using the media recorder,
     then we store in a file under "audio/".
     So right now in order to play this audio and draw the wavebar UI we need to decoded this file using the MediaCodec,
     so in the Val media codec we use the MediaCodec to get the right decoder that matches our audio and mime has our audio type"

     createDecoderByType("audio/mp4a-latm")
        ↓
     Android:
     - Looks for AAC decoder
     - Prefers hardware decoder
     - Falls back to software if needed
     - Allocates internal buffers

     If Android cannot decode this format, this line CRASHES.
     */
    val codec = MediaCodec.createDecoderByType(mime)

    /*so here in codec.configure we are telling the decoded the meta data of the encoded file and how it's organized so it can decoded it well,
      that why we give it our format because without it our decoded will not know how to decoded the file since it's doesn't know it's format and how it's organized.
      While the surface is zero because it's used for the videos,
      and the crypto is DRM for used for the copyright protection,
      and in flag is responsible for the modes,
      when we set the flags to zero the we are saying that we are in the decoding mode

If you misuse it:

    Mistake:      	     Result:
     Wrong format	      Garbage PCM
     Missing format       Crash
     Wrong flag	          No output
*/


    codec.configure(format, null, null, 0)

    codec.start()

    val bufferInfo = MediaCodec.BufferInfo()
    val sampleBuffer = ShortArray(1024) // For PCM samples. PCN is translating the electric vibration or air vibration into numbers so the device can understand it because devices understand numbers

    var sawInputEOS = false
    var sawOutputEOS = false

    /*so this loop is responsible to fill the decoder buffer with the encoded data */
    while (!sawOutputEOS) {
        // Feed input buffers
        if (!sawInputEOS) {

            /* here we ask the decoder if there is a available buffer spot to store the encoded audio data there,
             if there is available spot we store it's index and if not then it return negative number,
             while the timeoutus 10000 is equal to 10 millisecond, this the time to search for the empty buffer spot*/
            val inputBufferIndex = codec.dequeueInputBuffer(10000)
            if (inputBufferIndex >= 0) {
                /* so here in inputBuffer after we get the index for the available buffer, then we create the variable input buffer to start save the available space inside this variable because this variable will hold the encoded data */
                val inputBuffer = codec.getInputBuffer(inputBufferIndex)!!

                /*here in this sample variable we read chunks of the encoded data and puts it directly into the inputBuffer. and it tells us how many bytes were read. If it returns -1, it means "The file is empty, we are done."*/
                val sampleSize = extractor.readSampleData(inputBuffer, 0)

                if (sampleSize < 0) {
                    codec.queueInputBuffer(
                        inputBufferIndex,
                        0,
                        0,
                        0,
                        MediaCodec.BUFFER_FLAG_END_OF_STREAM
                    )
                    sawInputEOS = true
                } else {
                    val presentationTimeUs = extractor.sampleTime
                    codec.queueInputBuffer(
                        inputBufferIndex,
                        0,
                        sampleSize,
                        presentationTimeUs,
                        0
                    )
                    extractor.advance()
                }
            }
        }

        // Get decoded PCM samples
        val outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, 10000)
        if (outputBufferIndex >= 0) {
            val outputBuffer = codec.getOutputBuffer(outputBufferIndex)!!
            outputBuffer.order(ByteOrder.LITTLE_ENDIAN)

            // PCM samples are 16-bit → read as shorts
            val shorts = outputBuffer.asShortBuffer()
            val numSamples = shorts.remaining()
            if (numSamples > 0) {
                shorts.get(sampleBuffer, 0, minOf(numSamples, sampleBuffer.size))

                // Convert samples to amplitudes
                for (i in 0 until minOf(numSamples, sampleBuffer.size)) {
                    val amp = abs(sampleBuffer[i].toInt())
                    amplitudes.add(amp)
                }
            }

            outputBuffer.clear()
            codec.releaseOutputBuffer(outputBufferIndex, false)

            if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) {
                sawOutputEOS = true
            }
        }
    }

    codec.stop()
    codec.release()
    extractor.release()

    val chunkSize = 2000 // Number of PCM samples per bar
    val chunked = amplitudes.chunked(chunkSize) { chunk ->
        chunk.maxOrNull() ?: 0
    }

    // Normalize amplitudes for UI: scale 0..maxAmp → 0..255
    val maxAmp = chunked.maxOrNull()?.toFloat()  ?: 1f
    val normalized = chunked.map { (it  / maxAmp * 255).toInt().coerceIn(0, 255) }

    return@withContext normalized
}


//suspend fun extractAudioData(file: String): List<Int> = withContext(Dispatchers.IO) {
//    val audioData = extractAudioDataWithDuration(file)
//    return@withContext audioData.amplitudes
//}
//
//suspend fun extractAudioDataWithDuration(file: String): AudioData = withContext(Dispatchers.IO) {
//    try {
//        // Use MediaMetadataRetriever for more accurate and simpler extraction
//        val retriever = MediaMetadataRetriever()
//        retriever.setDataSource(file)
//
//        // Get duration from metadata
//        val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
//        val durationMs = durationStr?.toLongOrNull() ?: 1000L
//
//        // Get basic audio info
//        val sampleRateStr =
//            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE)
//
//        Log.d("ExtractAudioData", "Using simple extraction for ${durationMs}ms audio")
//
//        // Create amplitude points based on duration - one every 100ms for accuracy
//        val amplitudeIntervalMs = 100L
//        val totalPoints = (durationMs / amplitudeIntervalMs).toInt().coerceAtLeast(10)
//
//        // Use a simple approach: read file size and create realistic amplitude pattern
//        val fileSize = File(file).length()
//        val amplitudes = generateAmplitudesFromFile(file, totalPoints, durationMs)
//
//        retriever.release()
//
//        Log.d(
//            "ExtractAudioData",
//            "Generated ${amplitudes.size} amplitude points for ${durationMs}ms"
//        )
//        Log.d(
//            "ExtractAudioData",
//            "Sample amplitudes: ${amplitudes.take(5)}...${amplitudes.takeLast(5)}"
//        )
//
//        return@withContext AudioData(amplitudes, durationMs)
//
//    } catch (e: Exception) {
//        Log.e("ExtractAudioData", "Error in simple extraction", e)
//        return@withContext AudioData(getDefaultAmplitudes(), 5000L)
//    }
//}
//
//private fun generateAmplitudesFromFile(
//    filePath: String,
//    pointCount: Int,
//    durationMs: Long
//): List<Int> {
//    return try {
//        val file = File(filePath)
//        val bytes = file.readBytes()
//        val amplitudes = mutableListOf<Int>()
//
//        // Simple but effective approach: sample bytes from file at regular intervals
//        val bytesPerPoint = bytes.size / pointCount
//
//        for (i in 0 until pointCount) {
//            val startIndex = i * bytesPerPoint
//            val endIndex = minOf((i + 1) * bytesPerPoint, bytes.size)
//
//            // Calculate amplitude from raw bytes in this segment
//            var sum = 0.0
//            var maxByte = 0
//            var count = 0
//
//            for (j in startIndex until endIndex) {
//                val byteValue = abs(bytes[j].toInt())
//                sum += byteValue * byteValue
//                maxByte = maxOf(maxByte, byteValue)
//                count++
//            }
//
//            if (count > 0) {
//                val rms = sqrt(sum / count)
//                val amplitude = ((rms * 0.7 + maxByte * 0.3) * 2.0).toInt().coerceIn(10, 255)
//                amplitudes.add(amplitude)
//            } else {
//                amplitudes.add(30) // Default low amplitude
//            }
//        }
//
//        // Add some variation to make it look more natural
//        return amplitudes.mapIndexed { index, amp ->
//            val variation = (index % 7) * 5 - 15 // Small controlled variation
//            (amp + variation).coerceIn(10, 255)
//        }
//
//    } catch (e: Exception) {
//        Log.e("ExtractAudioData", "Error generating amplitudes from file", e)
//        // Return a pattern that varies based on file characteristics
//        getVariablePattern(pointCount)
//    }
//}
//
//private fun getVariablePattern(count: Int): List<Int> {
//    val pattern = mutableListOf<Int>()
//    for (i in 0 until count) {
//        // Create a more interesting pattern based on position
//        val baseAmplitude = 40 + (i * 3) % 50
//        val wave = (kotlin.math.sin(i * 0.3) * 30).toInt()
//        val amplitude = (baseAmplitude + wave).coerceIn(15, 200)
//        pattern.add(amplitude)
//    }
//    return pattern
//}
//
//private fun getDefaultAmplitudes(): List<Int> {
//    return listOf(
//        45, 75, 35, 90, 55, 30, 80, 40, 95, 60, 25, 70, 50, 85, 45,
//        65, 35, 90, 50, 75, 40, 85, 30, 95, 55, 70, 45, 80, 35, 60,
//        75, 40, 90, 25, 85, 55, 70, 45, 95, 35, 80, 50, 65, 40, 90,
//        30, 75, 60, 85, 45, 70, 35, 95, 50, 80, 40, 65, 55, 90, 45
//    )
//}