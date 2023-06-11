package com.example.musicapp.earTreiningSection

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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

class GuessMelody : Fragment() {
    private val floatSpeed = 1.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
                                style = MaterialTheme.typography.h4,
                            )
                            Row(
                                Modifier
                                    .horizontalScroll(scrollState)
                                    .fillMaxWidth()
                            ) {
                                PianoRoll(
                                    startNote = Note(PitchClass.C, 3),
                                    endNote = Note(PitchClass.C, 4),
                                    options = PianoRollOptions(highlightedNotes = selectedNote.value.chord)
                                ) {
                                    selectedNote.value = it.toString()
//                                    Log.d("hello", selectedNote.value.toLowerCase())
                                    val sound = context.resources.getIdentifier(
                                        selectedNote.value.toLowerCase(), "raw",
                                        context.packageName
                                    )
                                    if (sound != 0) {
                                        var mediaPlr = MediaPlayer.create(context, sound)
                                        mediaPlr.start()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}