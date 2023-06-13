package com.example.musicapp.earTreiningSection

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.citizenwarwick.pianoroll.PianoChord
import com.example.musicapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource

class GuessNotes : Fragment() {
    val charSet: Set<Char> = setOf('A', 'B', 'C', 'D', 'E', 'F', 'G')
    var setButtonVisibilityBool by mutableStateOf(0f)

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
                        val noteToGuess = rememberSaveable { mutableStateOf("d3") }
                        val selectedNote = rememberSaveable { mutableStateOf("C0") }

                        Column {
                            Row(
                                Modifier
                                    .fillMaxWidth(),
                            ) {
                                Text(
//                                    "Guess a note ${noteToGuess.value}",
                                    "Guess a note",
                                    style = MaterialTheme.typography.h4
                                )
                                Spacer(Modifier.weight(1f))
                                Image(
                                    painter = painterResource(id = R.drawable.play_sound),
                                    contentDescription = stringResource(id = R.string.app_name),
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clickable {
                                            val sound = context.resources.getIdentifier(
                                                noteToGuess.value.toLowerCase(), "raw",
                                                context.packageName
                                            )
                                            if (sound != 0) {
                                                var mediaPlr = MediaPlayer.create(context, sound)
                                                mediaPlr.start()
                                            }
                                        }
                                )
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (selectedNote.value.toLowerCase() == noteToGuess.value.toLowerCase()) {
                                    setButtonVisibilityBool = 1f
                                    PianoRoll(
                                        startNote = Note(PitchClass.C, 3),
                                        endNote = Note(PitchClass.B, 3),
                                        options = PianoRollOptions(
                                            highlightedNotes = selectedNote.value.chord,
                                            highlightKeyColor = Color.Green,
                                            sizeScale = 0.9f
                                        )
                                    ) {
                                        selectedNote.value = it.toString()
                                        val sound = context.resources.getIdentifier(
                                            selectedNote.value.toLowerCase(), "raw",
                                            context.packageName
                                        )
                                        if (sound != 0) {
                                            var mediaPlr = MediaPlayer.create(context, sound)
                                            mediaPlr.start()
                                        }
                                    }
                                } else {
                                    PianoRoll(
                                        startNote = Note(PitchClass.C, 3),
                                        endNote = Note(PitchClass.B, 3),
                                        options = PianoRollOptions(
                                            highlightedNotes = selectedNote.value.chord,
                                            highlightKeyColor = Color.Yellow,
                                            sizeScale = 0.9f
                                        )
                                    ) {
                                        selectedNote.value = it.toString()
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

                            val state = setButtonVisibilityBool
                            Column(
                                modifier = Modifier
                                    .alpha(state)
                                    .wrapContentWidth()
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        setButtonVisibilityBool = 0f
                                        selectedNote.value = "C0"
                                        val randomChar = charSet.random()
                                        val octave: Int = 3
                                        val nextNoteToGuess = "$randomChar$octave"
                                        noteToGuess.value = nextNoteToGuess
                                    },
                                    shape = RoundedCornerShape(5.dp),
                                    elevation = ButtonDefaults.elevation(5.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = colorResource(id = R.color.yello),
                                        contentColor = Color.Black,
                                    ),
                                ) {
                                    Text(text = "Try next", fontSize = 20.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}