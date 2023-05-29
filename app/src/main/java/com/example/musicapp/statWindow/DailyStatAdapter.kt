package com.example.musicapp.statWindow

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.StatBinding
import com.example.musicapp.models.DailyStat

class DailyStatAdapter (
    private val values: List<DailyStat>
) : RecyclerView.Adapter<DailyStatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(StatBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.day.text = item.date.toString()
        if(item.exercises == 0) {
            holder.exercises.text = "No exercises completed"
        } else holder.exercises.text = item.exercises.toString()
        if(item.time == 0) {
            holder.time.text = "--"
        } else holder.time.text = item.time.toString()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: StatBinding) : RecyclerView.ViewHolder(binding.root) {
        val day: TextView = binding.dayStatItem
        val exercises: TextView = binding.exerciseCountStatItem
        val time: TextView = binding.timeCountStatItem
    }

}