package com.example.musicapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation


class PianoGame : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_piano_game, container, false)

        view.findViewById<TextView>(R.id.textView3).setOnClickListener(View.OnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_piano_game_to_home)
        })

        return view
    }

}