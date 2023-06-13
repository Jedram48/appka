package com.example.musicapp

import ViewPagerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
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

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.example.musicapp.earTreiningSection.GuessIntervals
import com.example.musicapp.earTreiningSection.GuessMelody
import com.example.musicapp.earTreiningSection.GuessNotes
import com.google.android.material.tabs.TabLayout

class EarTrainingSection : Fragment(R.layout.fragment_ear_training_section) {
    private lateinit var pager: ViewPager
    private lateinit var tab: TabLayout
    private lateinit var bar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ear_training_section, container, false)
        pager = view.findViewById(R.id.viewPager)
        tab = view.findViewById(R.id.tabs)
        bar = view.findViewById(R.id.toolbar)

        // To make our toolbar show the application
        // we need to give it to the ActionBar
        (requireActivity() as AppCompatActivity).setSupportActionBar(bar)

        // Initializing the ViewPagerAdapter
        val adapter = ViewPagerAdapter(parentFragmentManager)

        // add fragment to the list
        adapter.addFragment(GuessNotes(), "Notes")
        adapter.addFragment(GuessIntervals(), "Intervals")
        adapter.addFragment(GuessMelody(), "Melody")

        // Adding the Adapter to the ViewPager
        pager.adapter = adapter

        // bind the viewPager with the TabLayout.
        tab.setupWithViewPager(pager)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}