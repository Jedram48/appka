package com.example.musicapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.musicapp.models.Exercise
import com.example.musicapp.models.ExerciseLog
import java.time.LocalDate

@Dao
interface ExerciseDao {

    @Upsert
    suspend fun upsertExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Insert(onConflict = 1)
    suspend fun logExercise(exerciseLog: ExerciseLog)


    @Query("SELECT * FROM exercise_log_table WHERE date = :date")
    fun getLogByDate(date: LocalDate): LiveData<List<ExerciseLog>>

    @Query("SELECT * FROM exercise_table ORDER BY id ASC")
    fun getAllExercises(): LiveData<List<Exercise>>

    @Query("SELECT * FROM exercise_log_table WHERE date BETWEEN :start AND :end")
    fun getMonthLog(start: LocalDate, end: LocalDate): LiveData<List<ExerciseLog>>

}