package com.example.musicapp

import ViewPagerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.example.musicapp.db.ExerciseViewModel
import com.example.musicapp.earTreiningSection.GuessIntervals
import com.example.musicapp.earTreiningSection.GuessMelody
import com.example.musicapp.earTreiningSection.GuessNotes
import com.example.musicapp.models.ExerciseLog
import com.google.android.material.tabs.TabLayout
import java.time.LocalDate
import java.time.LocalTime

class EarTrainingSection : Fragment(R.layout.fragment_ear_training_section) {
    private lateinit var pager: ViewPager
    private lateinit var tab: TabLayout
    private lateinit var bar: Toolbar
    private lateinit var navController: NavController
    private lateinit var dbViewModel: ExerciseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ear_training_section, container, false)
        pager = view.findViewById(R.id.viewPager)
        dbViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)
        tab = view.findViewById(R.id.tabs)
        bar = view.findViewById(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(bar)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        bar.setupWithNavController(navController, appBarConfiguration)
        dbViewModel.logExercise(ExerciseLog(0, 2, LocalDate.now(), LocalTime.now(), 10, 100 ))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}