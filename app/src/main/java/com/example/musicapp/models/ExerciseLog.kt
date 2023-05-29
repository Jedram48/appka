package com.example.musicapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date

@Entity(tableName = "exercise_log_table")
data class ExerciseLog(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Int,
    @ColumnInfo(name = "date")
    val date: LocalDate,
    @ColumnInfo(name = "time_stamp")
    val timeStamp: LocalTime,
    @ColumnInfo(name = "duration")
    val duration: Int,
    @ColumnInfo(name = "score")
    val score: Int
)
