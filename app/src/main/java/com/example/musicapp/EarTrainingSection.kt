package com.example.musicapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.Navigation
import com.citizenwarwick.music.Note
import com.citizenwarwick.music.PitchClass
import com.citizenwarwick.music.chord
import com.citizenwarwick.pianoroll.PianoRoll
import com.citizenwarwick.pianoroll.PianoRollOptions


class EarTrainingSection : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Text(text = "Hello world.")
                MaterialTheme {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        PianoRoll(
                            startNote = Note(PitchClass.C, 0),
                            endNote = Note(PitchClass.C, 1),
                            options = PianoRollOptions(
                                highlightedNotes = "C0 E0 G0 B0".chord
                            )
                        )
                    }
                }
            }
        }
    }

}