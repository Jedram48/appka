package com.example.musicapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation


class RhythmSection : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_rhythm_section, container, false)

        view.findViewById<TextView>(R.id.textView1).setOnClickListener(View.OnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_rhythm_section_to_home)
        })

        return view
    }

}