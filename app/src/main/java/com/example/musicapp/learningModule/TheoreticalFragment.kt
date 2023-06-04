package com.example.musicapp.learningModule
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import java.util.*
import kotlin.collections.ArrayList


class TheoreticalFragment : Fragment(R.layout.fragment_second) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<LanguageData>()
    private lateinit var adapter: LanguageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)

//        this.setContentView(R.layout.fragment_second)

        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        addDataToList()
        adapter = LanguageAdapter(mList)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
        return view
    }

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<LanguageData>()
            for (i in mList) {
                if (i.title.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
//                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun addDataToList() {
        mList.add(
            LanguageData(
                "Unison",
                R.drawable.unison,
                "Unisone is an interval that consists of the same note. For example two different instruments might play exactly the same note in a piece of music."
            )
        )
        mList.add(
            LanguageData(
                "Semitone",
                R.drawable.semitone,
                "Also called a half step or a half tone, is the smallest musical interval and is considered as dissonant. "

            )
        )
        mList.add(
            LanguageData(
                "Tone",
                R.drawable.tone,
                "A major second (sometimes also called whole tone or a whole step).An alternate spelling of major second is diminished third."
            )
        )
        mList.add(
            LanguageData(
                "Major 3rd",
                R.drawable.empty,
                "Empty"

            )
        )
        mList.add(
            LanguageData(
                "Perfect 4th",
                R.drawable.empty,
                "Empty"
            )
        )
        mList.add(
            LanguageData(
                "Tritone",
                R.drawable.empty,
                "Empty"
            )
        )
        mList.add(
            LanguageData(
                "Perfect 5th",
                R.drawable.empty,
                "Empty"
            )
        )
        mList.add(
            LanguageData(
                "Minor 6th",
                R.drawable.empty,
                "Empty"
            )
        )
        mList.add(
            LanguageData(
                "Major 6th",
                R.drawable.empty,
                "Empty"
            )
        )
        mList.add(
            LanguageData(
                "Minor 7th",
                R.drawable.empty,
                "Empty"
            )
        )
        mList.add(
            LanguageData(
                "Major 7th",
                R.drawable.empty,
                "Empty"
            )
        )
        mList.add(
            LanguageData(
                "Perfect 8ve",
                R.drawable.empty,
                "Empty"
            )
        )
    }

}