package com.example.musicapp.earTreiningSection

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.citizenwarwick.music.Note
import com.citizenwarwick.music.PitchClass
import com.citizenwarwick.music.chord
import com.citizenwarwick.pianoroll.PianoRoll
import com.citizenwarwick.pianoroll.PianoRollOptions
import com.example.musicapp.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random


@SuppressLint("MutableCollectionMutableState")
class GuessMelody : Fragment() {
    private val charSet: Set<Char> = setOf('A', 'B', 'C', 'D', 'E', 'F', 'G')
    var setButtonVisibilityBool by mutableStateOf(0f)
    var melodyToGuess: ArrayList<String> by mutableStateOf(ArrayList())
    var userMelody: ArrayList<String> by mutableStateOf(ArrayList())
    var finished: Boolean by mutableStateOf(false)
    var temp = ""
    init {
        melodyToGuess = initMelody()
    }

    private fun initMelody(): java.util.ArrayList<String> {
        val duration = Random.nextInt(3, 8)
        val notes: ArrayList<String> = ArrayList()

        for (i in 1..duration){
            var temp = randomNote()
            while (notes.contains(temp)){
                temp = randomNote()
            }
            notes.add(temp)
        }
        return notes
    }

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
                        val selectedNote = rememberSaveable { mutableStateOf("") }

                        Column {
                            Text(
                            "Guess melody $melodyToGuess",
                            style = MaterialTheme.typography.h4
                        )
                            Row(
                                Modifier
                                    .fillMaxWidth(),
                            ) {

                                Spacer(Modifier.weight(1f))
                                Image(
                                    painter = painterResource(id = R.drawable.play_sound),
                                    contentDescription = stringResource(id = R.string.app_name),
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clickable {
                                            playSound(melodyToGuess, context)
                                        }
                                )
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (selectedNote.value!=temp){
                                    temp = selectedNote.value
                                }
                                if (selectedNote.value!= "") {
                                    userMelody.add(selectedNote.value)
                                }
                                var options : PianoRollOptions

                                if (melodyToGuess == userMelody || finished) {

                                    setButtonVisibilityBool = 1f
                                    var notes: String = ""
                                    for (i in userMelody.indices) {
                                        if (i < userMelody.lastIndex) {
                                            notes =
                                                "${notes.toUpperCase()}${userMelody[i].toUpperCase()} "

                                        } else {
                                            notes =
                                                "${notes.toUpperCase()}${userMelody[i].toUpperCase()}"
                                        }
                                    }

                                    var playNote: String
                                    if (notes == "") {
                                        playNote = "C0"
                                    } else {
                                        playNote = notes
                                    }

                                    finished = true

                                    options = PianoRollOptions(
                                        highlightedNotes = playNote.chord,
                                        highlightKeyColor = Color.Green,
                                        sizeScale = 0.9f
                                    )
                                } else {
                                    var playNote : String
                                    if (selectedNote.value == "") {
                                        playNote = "C0"
                                    } else {
                                        playNote = selectedNote.value
                                    }

                                    options = PianoRollOptions(
                                        highlightedNotes = playNote.chord,
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
                                Text(text = "Your notes: $userMelody", fontSize = 20.sp)
                            }

                            val state = setButtonVisibilityBool
                            Row(
                                modifier = Modifier
                                    .alpha(1f - state)
                                    .wrapContentWidth()
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        userMelody = arrayListOf()
                                        selectedNote.value = ""
                                    },
                                    shape = RoundedCornerShape(5.dp),
                                    elevation = ButtonDefaults.elevation(5.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.LightGray,
                                        contentColor = Color.Black,
                                    ),
                                ) {
                                    Text(text = "Clear notes", fontSize = 20.sp)

                                }
                            }

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
                                        userMelody = arrayListOf()
                                        selectedNote.value = ""
                                        melodyToGuess = initMelody()
                                        finished = false
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

    private val lock = Mutex()
    fun playOneSound(name:String, context: Context){
        GlobalScope.launch {
            lock.withLock {
                println("$name started")
                val sound = context.resources.getIdentifier(
                    name.toLowerCase(), "raw",
                    context.packageName
                )
                if (sound != 0) {
                    var mediaPlr = MediaPlayer.create(context, sound)
                    mediaPlr.start()
                }
                delay(1000)
                println("$name completed")
            }
        }
    }
    private fun playSound(melodyToGuess: java.util.ArrayList<String>, context: Context) {
        var i = 0;
        while ( i < melodyToGuess.size ) {
            playOneSound(melodyToGuess[i], context)
            i++
        }
    }

    private fun randomNote(): String {
        val randomChar = charSet.random()
        val octave: Int = 3
        return "$randomChar$octave"
    }
}