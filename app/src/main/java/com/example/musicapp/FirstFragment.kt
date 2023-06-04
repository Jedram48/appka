package com.example.musicapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.musicapp.R

class FirstFragment:Fragment(R.layout.fragment_first) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        view.findViewById<TextView>(R.id.rhythm_section).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_home_to_rhythm_section)
        }
        view.findViewById<TextView>(R.id.ear_training_section).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_home_to_ear_training_section)
        }
        view.findViewById<TextView>(R.id.piano_game).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_home_to_piano_game)
        }

        return view
    }
}

