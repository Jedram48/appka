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


class TheoreticalFragment : Fragment(R.layout.fragment_learning) {
    private lateinit var parentRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<ChildData>()
    private lateinit var parentList: ArrayList<ParentItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_learning, container, false)
        parentRecyclerView = view.findViewById(R.id.parentRecyclerView)
        parentList = ArrayList()
        val adapter = ParentRecyclerViewAdapter(parentList)



        searchView = view.findViewById(R.id.searchView)

        parentRecyclerView.setHasFixedSize(true)
        parentRecyclerView.layoutManager = LinearLayoutManager(activity)
        prepareData()
        parentRecyclerView.adapter = adapter

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
            val filteredList = ArrayList<ChildData>()
            for (i in mList) {
                if (i.title.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
//                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
//                adapter.setFilteredList(filteredList)
            }
        }
    }


    private fun prepareData() {

        val childItems1 = ArrayList<ChildItem>()
        childItems1.add(ChildItem(
                "Unison",
                R.drawable.unison,
                "Unisone is an interval that consists of the same note. For example two different instruments might play exactly the same note in a piece of music."
            ))
        childItems1.add(ChildItem(
                "Semitone",
                R.drawable.semitone,
                "Also called a half step or a half tone, is the smallest musical interval and is considered as dissonant. "

            ))
        childItems1.add(ChildItem(
                "Tone",
                R.drawable.tone,
                "A major second (sometimes also called whole tone or a whole step).An alternate spelling of major second is diminished third."
            ))
        childItems1.add(ChildItem(
                "Major 3rd",
                R.drawable.empty,
                "Empty"
            ))
        childItems1.add(ChildItem(
                "Perfect 4th",
                R.drawable.empty,
                "Empty"
            )
        )
        childItems1.add(ChildItem(
                "Tritone",
                R.drawable.empty,
                "Empty"
            )
        )
        childItems1.add(ChildItem(
                "Perfect 5th",
                R.drawable.empty,
                "Empty"
            )
        )
        childItems1.add(ChildItem(
                "Minor 6th",
                R.drawable.empty,
                "Empty"
            )
        )
        childItems1.add(ChildItem(
                "Major 6th",
                R.drawable.empty,
                "Empty"
            )
        )
        childItems1.add(ChildItem(
                "Minor 7th",
                R.drawable.empty,
                "Empty"
            )
        )
        childItems1.add(ChildItem(
                "Major 7th",
                R.drawable.empty,
                "Empty"
            )
        )
        childItems1.add(ChildItem(
                "Perfect 8ve",
                R.drawable.empty,
                "Empty"
            )
        )

        parentList.add(ParentItem("Intervals sounds", R.drawable.music_intervals, childItems1))


        val childItem2 = ArrayList<ChildItem>()
        childItem2.add(ChildItem("Harmonic", R.drawable.green, "empty"))
        childItem2.add(ChildItem("Melodic", R.drawable.green, "empty"))
        parentList.add(
            ParentItem(
                "Harmonic vs Melodic intervals",
                R.drawable.mozart,
                childItem2
            )
        )
        val childItem3 = ArrayList<ChildItem>()
        childItem3.add(ChildItem("Perfect intervals", R.drawable.green, "empty"))
        childItem3.add(ChildItem("Minor intervals", R.drawable.green, "empty"))
        childItem3.add(ChildItem("Major intervals", R.drawable.green, "empty"))
        childItem3.add(ChildItem("Diminished intervals", R.drawable.green, "empty"))
        childItem3.add(ChildItem("Augmented intervals", R.drawable.green, "empty"))
        childItem3.add(ChildItem("Consonant intervals", R.drawable.green, "empty"))
        childItem3.add(ChildItem("Dissonant intervals", R.drawable.green, "empty"))
        parentList.add(
            ParentItem(
                "Interval qualities",
                R.drawable.mozart,
                childItem3
            )
        )
        val childItem4 = ArrayList<ChildItem>()
        childItem4.add(ChildItem("minor ↔ major", R.drawable.green, "EMPTY"))
        childItem4.add(ChildItem("perfect ↔ perfect", R.drawable.green, "EMPTY"))
        childItem4.add(ChildItem("diminished ↔ augmented", R.drawable.green, "EMPTY"))
        childItem4.add(ChildItem("unison ↔ octave", R.drawable.green, "EMPTY"))
        childItem4.add(ChildItem("second ↔ seventh", R.drawable.green, "EMPTY"))
        childItem4.add(ChildItem("third ↔ sixth", R.drawable.green, "EMPTY"))
        childItem4.add(ChildItem("fourth ↔ fifth", R.drawable.green, "EMPTY"))
        parentList.add(
            ParentItem(
                "Interval inversions",
                R.drawable.mozart,
                childItem4
            )
        )
        val childItem5 = ArrayList<ChildItem>()
        childItem5.add(ChildItem("Definition", R.drawable.green, "EMPTY"))
        childItem5.add(ChildItem("The diatonic major scale", R.drawable.green, "EMPTY"))
        childItem5.add(ChildItem("The diatonic minor scale", R.drawable.green, "EMPTY"))
        parentList.add(
            ParentItem(
                "Circle of fifths",
                R.drawable.mozart,
                childItem5
            )
        )
    }
}