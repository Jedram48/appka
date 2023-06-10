package com.example.musicapp

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.citizenwarwick.music.Note
import com.citizenwarwick.music.PitchClass
import com.citizenwarwick.music.chord
import com.citizenwarwick.pianoroll.PianoRoll
import com.citizenwarwick.pianoroll.PianoRollOptions


class EarTrainingSection : Fragment() {
    private var soundPool: SoundPool? = null
    private var integerSoundIDa: Int? = null
    private var integerSoundIDb: Int? = null
    private var integerSoundIDc: Int? = null
    private var integerSoundIDd: Int? = null
    private var integerSoundIDe: Int? = null
    private var integerSoundIDf: Int? = null
    private var integerSoundIDg: Int? = null

    private val floatSpeed = 1.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val builder = SoundPool.Builder()
//        soundPool = builder.build()
//
//        integerSoundIDa = soundPool.load(this, R.raw.a3, 1)
//        integerSoundIDb = soundPool.load(this, R.raw.b3, 1)
//        integerSoundIDc = soundPool.load(this, R.raw.c3, 1)
//        integerSoundIDd = soundPool.load(this, R.raw.d3, 1)
//        integerSoundIDe = soundPool.load(this, R.raw.e3, 1)
//        integerSoundIDf = soundPool.load(this, R.raw.f3, 1)
//        integerSoundIDg = soundPool.load(this, R.raw.g3, 1)
        return ComposeView(requireContext()).apply {
            setContent {
                Text(text = "Hello world.")
                MaterialTheme {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        val selectedNote = rememberSaveable { mutableStateOf("C0") }
                        val scrollState = remember { ScrollState(0) }

                        Column {
                            Text(
                                "You selected ${selectedNote.value}",
                                style = MaterialTheme.typography.h4
                            )
                            Row(
                                Modifier
                                    .horizontalScroll(scrollState)
                                    .fillMaxWidth()
                            ) {
                                PianoRoll(
                                    startNote = Note(PitchClass.C, 0),
                                    endNote = Note(PitchClass.C, 1),
                                    options = PianoRollOptions(highlightedNotes = selectedNote.value.chord)
                                ) {
                                    selectedNote.value = it.toString()
                                    var mp = MediaPlayer.create(context, R.raw.b3)
                                    mp.start()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @Composable
    fun playAudio(context : Context){
//        mp.setDataSource(this, Uri.parse("android.resource://"+this.pa))

    }
}