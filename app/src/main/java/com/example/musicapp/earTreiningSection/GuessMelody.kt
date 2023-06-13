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
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.musicapp.R

class GuessMelody : Fragment() {
    private val floatSpeed = 1.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
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
                            Row(
                                Modifier
//                                    .horizontalScroll(scrollState)
                                    .fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Guess a melody ${selectedNote.value}",
                                    style = MaterialTheme.typography.h4
                                )
                                Spacer(Modifier.weight(1f))
                                Image(
                                    painter = painterResource(id = R.drawable.play_sound),
                                    contentDescription = stringResource(id = R.string.app_name),
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                            Row(
                                Modifier
                                    .horizontalScroll(scrollState)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                PianoRoll(
                                    startNote = Note(PitchClass.C, 3),
                                    endNote = Note(PitchClass.B, 3),
                                    options = PianoRollOptions(
                                        highlightedNotes = selectedNote.value.chord,
                                        sizeScale = 0.9f
                                    )
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