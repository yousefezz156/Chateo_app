import android.media.MediaCas
import android.media.MediaExtractor
import android.media.MediaFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.ByteBuffer
import kotlin.math.abs

suspend fun extractAudioData(file : String) : List<Int> = withContext(Dispatchers.IO){
    val amplitude = mutableListOf<Int>()

    try{
        val extractor = MediaExtractor()
        extractor.setDataSource(file)

        val format = extractor.getTrackFormat(0)
        extractor.selectTrack(0)

        val maxBuffer = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE)
        val buffer = ByteBuffer.allocate(maxBuffer)

        while(true){
            val sampledata = extractor.readSampleData(buffer, 0)

            if(sampledata <0)
                break

            buffer.rewind()

            for(i in 0 until sampledata){
                val amp = abs(buffer.get().toInt())
                amplitude.add(amp)
            }

            extractor.advance()
        }

    }catch (e : Exception){
        e.printStackTrace()
    }

    return@withContext amplitude
}