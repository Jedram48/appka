package com.example.musicapp.statWindow

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.db.ExerciseViewModel
import com.example.musicapp.models.DailyStat
import com.example.musicapp.models.ExerciseLog
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters


class StatFragment : Fragment() {

    private lateinit var mExerciseViewModel: ExerciseViewModel
    private val exerciseGoal = 10
    private val scoreGoal = 1000
    private val durationGoal = 1000

    private var data = listOf<ExerciseLog>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stat, container, false)
        val list = view.findViewById<RecyclerView>(R.id.recyclerView)

        mExerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]

        mExerciseViewModel.getMonthLogData.observe(viewLifecycleOwner, Observer { logs ->
            data = logs
            setTodayProgress(view)
            setWeekProgress(view)
            setMonthProgress(view)
        })


        return view
    }


    private  fun setTodayProgress(view: View) {
        val score = getDayScore(data, LocalDate.now())
        val duration = getDayDuration(data, LocalDate.now())
        val exercises = getDayExercises(data, LocalDate.now())


        val scoreBar = view.findViewById<ProgressBar>(R.id.pointBar)
        scoreBar.max = scoreGoal
        scoreBar.progress = score
        val durationBar = view.findViewById<ProgressBar>(R.id.timeBar)
        durationBar.max = durationGoal
        durationBar.progress = duration
        val exerciseBar = view.findViewById<ProgressBar>(R.id.exerciseBar)
        exerciseBar.max = exerciseGoal
        exerciseBar.progress = exercises

        val scoreText = view.findViewById<TextView>(R.id.pointCount)
        scoreText.text = "$score"
        val durationText = view.findViewById<TextView>(R.id.timeCount)
        durationText.text = "$duration"
        val exerciseText = view.findViewById<TextView>(R.id.exerciseCount)
        exerciseText.text = "$exercises"
    }

    private fun setWeekProgress(view: View) {

        val lineChart: LineChart = view.findViewById(R.id.chart)

        val entries: MutableList<Entry> = mutableListOf()

        val startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
        for (i in 0 until 7) {
            val currentDate = startOfWeek.plusDays(i.toLong())
            entries += (Entry(i.toFloat(), getDayExercises(data, currentDate).toFloat()))
        }

        val dataSet = LineDataSet(entries, "Example Data")
        dataSet.setDrawValues(false)
        dataSet.color = Color.RED
        dataSet.valueTextColor = Color.BLACK

        val dataSets: MutableList<ILineDataSet> = ArrayList()
        dataSets.add(dataSet)

        val lineData = LineData(dataSets)
        lineChart.data = lineData
        lineChart.setDrawGridBackground(false)
        lineChart.description.isEnabled = false
        lineChart.axisRight.isEnabled = false
        lineChart.legend.isEnabled = false

        val daysOfWeek = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        val xAxis: XAxis = lineChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(daysOfWeek)
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        lineChart.invalidate()

    }

    fun setMonthProgress(view: View) {

        val statList = mutableListOf<DailyStat>()

        val today = LocalDate.now()
        val firstDayOfMonth = today.withDayOfMonth(1)

        var currentDate = today
        while (!currentDate.isBefore(firstDayOfMonth)) {
            statList += if(getDayData(data, currentDate).isNotEmpty())
                DailyStat(currentDate, getDayExercises(data, currentDate), getDayDuration(data, currentDate))
            else
                DailyStat(currentDate, 0, 0)
            currentDate = currentDate.minusDays(1)
        }


        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =  LinearLayoutManager(context)
        recyclerView.adapter = DailyStatAdapter(statList)

    }

    private fun getDayData(logs: List<ExerciseLog>, day: LocalDate): List<ExerciseLog> {
        return logs.filter { it.date == day }
    }

    private fun getDayExercises(logs: List<ExerciseLog>, day: LocalDate): Int {
        return getDayData(logs, day).size
    }

    private fun getDayScore(logs: List<ExerciseLog>, day: LocalDate): Int {
        var score = 0
        for (exercise in getDayData(logs, day)) {
            score += exercise.score
        }
        return score
    }

    private fun getDayDuration(logs: List<ExerciseLog>, day: LocalDate): Int {
        var duration = 0
        for (exercise in getDayData(logs, day)) {
            duration += exercise.duration
        }
        return duration
    }

}