package com.example.musicapp.earTreiningSection

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.musicapp.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource

class GuessIntervals : Fragment() {
    private val charSet: Set<Char> = setOf('A', 'B', 'C', 'D', 'E', 'F', 'G')
    var setButtonVisibilityBool by mutableStateOf(0f)
    var intervalToGuess: Pair<String, String> by mutableStateOf(Pair("D3", "C3"))
    var userInterval: Pair<String, String> by mutableStateOf(Pair("", ""))
    var temp = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        val selectedNote = rememberSaveable { mutableStateOf("C0") }

                        Column {
                            Row(
                                Modifier
                                    .fillMaxWidth(),
                            ) {
                                Text(
                                    "Guess interval $intervalToGuess",
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
                                                intervalToGuess.first.toLowerCase(), "raw",
                                                context.packageName
                                            )
                                            val sound2 = context.resources.getIdentifier(
                                                intervalToGuess.second.toLowerCase(), "raw",
                                                context.packageName
                                            )
                                            if (sound != 0 && sound2 != 0) {
                                                var mediaPlr = MediaPlayer.create(context, sound)
                                                var mediaPlr2 = MediaPlayer.create(context, sound2)
                                                mediaPlr.start()
                                                mediaPlr2.start()
                                            }
                                        }
                                )
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (selectedNote.value!=temp){
                                    userInterval =
                                        addNoteToInterval(selectedNote.value, userInterval)
                                    temp = selectedNote.value
                                }

                                var options : PianoRollOptions
                                if (intervalToGuess == userInterval
                                    || (intervalToGuess.first == userInterval.second && intervalToGuess.second == userInterval.first)) {
                                    setButtonVisibilityBool = 1f
                                    val notes = userInterval.first + " " + userInterval.second
                                    options = PianoRollOptions(
                                        highlightedNotes = notes.chord,
                                        highlightKeyColor = Color.Green,
                                        sizeScale = 0.9f
                                    )
                                } else {
                                    options = PianoRollOptions(
                                        highlightedNotes = selectedNote.value.chord,
                                        highlightKeyColor = Color.Yellow,
                                        sizeScale = 0.9f
                                    )
                                }
                                PianoRoll(
                                    startNote = Note(PitchClass.C, 3),
                                    endNote = Note(PitchClass.B, 3),
                                    options = options
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
                            Row(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(text = "Your notes: ${userInterval.first} ${userInterval.second}", fontSize = 20.sp)
                            }

                            val state = setButtonVisibilityBool
                            Row(
                                modifier = Modifier
                                    .alpha(state)
                                    .wrapContentWidth()
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        setButtonVisibilityBool = 0f
                                        selectedNote.value = "C0"
                                        intervalToGuess = randomInterval()
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

    private fun randomInterval(): Pair<String, String> {
        val randomChar = charSet.random()
        var randomChar2 = charSet.random()
        while (randomChar2 == randomChar){
            randomChar2 = charSet.random()
        }
        val octave: Int = 3
        val nextNoteFirst = "$randomChar$octave"
        val nextNoteSecond = "$randomChar2$octave"
        return Pair(nextNoteFirst, nextNoteSecond)
    }

    private fun addNoteToInterval(
        note: String,
        interval: Pair<String, String>
    ): Pair<String, String> {
        if (note == "C0") return Pair("", "")
        if (interval.first == "") return Pair(note, "")
        if (interval.second == "") return Pair(interval.first, note)
        else return Pair(note, "")
    }
}