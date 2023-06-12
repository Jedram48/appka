package com.example.musicapp.rhythm

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.mutableStateListOf
import com.example.musicapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.math.abs


class Metronome(private val context: Context, private val tempo: Int) {
    private val mediaPlayer = MediaPlayer.create(context, R.raw.click)
    private var tickDuration: Long = 0L
    private var isRunning = false
    private val metronomeTicks = mutableStateListOf<Boolean>(true, false, false, false)
    private var timeStamps: List<Long> = listOf()
    private val rhythmSequence = mutableListOf<Boolean>()
    private var tickCount: Int = 3
    var barStart: Long = 0
    var barDuration: Float = 0f


    init {
        calculateTickDuration()
        generateRandomRhythm()
        calculateTimeStamps()
    }

    fun start() {
        if (isRunning) return

        isRunning = true

        CoroutineScope(Dispatchers.Default).launch {
            while (isRunning) {
                tick()
            }
        }

    }

    fun stop() {
        isRunning = false
        mediaPlayer.stop()
        metronomeTicks[0] = true
        for( i in 1 until metronomeTicks.size){
            metronomeTicks[i] = false
        }
    }

    fun getMetronomeTicks(): List<Boolean> {
        return metronomeTicks
    }

    fun getTickCount(): Int {
        return tickCount
    }

    fun checkIfTick(): Boolean {
        val now = System.currentTimeMillis()
        if (timeStamps.isNotEmpty() && now > timeStamps[0]) {
            for (stamp in timeStamps) {
                if (abs(now - stamp) < 200) {
                    return true
                }
            }
        }
        return false
    }

    private fun playTickSound() {
        mediaPlayer.start()
    }

    private fun generateRandomRhythm() {
        val rhythmLength = 8
        for (i in 0 until rhythmLength) {
            rhythmSequence.add(i, Math.random() < 0.5)
        }
    }

    fun getRhythmSequence(): List<Boolean> {
        return rhythmSequence
    }

    private fun tick(){
        playTickSound()
        metronomeTicks[tickCount] = false
        if(tickCount + 1 < 4){
            tickCount++
        }
        else {
            tickCount = 0
            timeStamps = emptyList()
            calculateTimeStamps()
        }
        metronomeTicks[tickCount] = true
        delay(tickDuration)
    }

    private fun calculateTimeStamps(){
        val now = System.currentTimeMillis()
        val diff = 60000 / (tempo * 2)
        for (i in 0 until 8){
            if(rhythmSequence[i])
                timeStamps = timeStamps + (now + (i * diff))
        }
        barStart = now
        barDuration = ((now + (8 * diff)) - now).toFloat()
    }

    private fun delay(duration: Long) {
        val endTime = System.nanoTime() + duration
        while (System.nanoTime() < endTime) {
            // Wait for the specified duration
        }
    }

    private fun calculateTickDuration() {
        val microsecondsPerBeat = TimeUnit.MINUTES.toMicros(1) / tempo
        val samplesPerTick = (microsecondsPerBeat * SAMPLE_RATE) / TimeUnit.SECONDS.toMicros(1)
        tickDuration = (samplesPerTick / SAMPLE_RATE) * TimeUnit.SECONDS.toNanos(1)
    }



    companion object {
        private const val SAMPLE_RATE = 44100
    }
}


