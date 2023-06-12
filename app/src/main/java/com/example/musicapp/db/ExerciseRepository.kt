package com.example.musicapp.db

import androidx.lifecycle.LiveData
import com.example.musicapp.models.Exercise
import com.example.musicapp.models.ExerciseLog
import java.time.LocalDate
import java.time.LocalTime

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    suspend fun upsertExercise(exercise: Exercise) {
        exerciseDao.upsertExercise(exercise)
    }

    suspend fun deleteExercise(exercise: Exercise) {
        exerciseDao.deleteExercise(exercise)
    }

    suspend fun logExercise(exerciseLog: ExerciseLog) {
        exerciseDao.logExercise(exerciseLog)
    }

    fun getAllExercises(): LiveData<List<Exercise>> {
        return exerciseDao.getAllExercises()
    }
    suspend fun insertDefaultExercises() {
            val defaultExercises = listOf(
                Exercise(1, "Rhythm Training Exercise 1", "Basic rhythm training", "Rhythm Training"),
                Exercise(2, "Rhythm Training Exercise 2", "Intermediate rhythm training", "Rhythm Training"),
                Exercise(3, "Rhythm Training Exercise 3", "Advanced rhythm training", "Rhythm Training"),
            )
            defaultExercises.forEach { exercise ->
                exerciseDao.upsertExercise(exercise)
        }
    }

    suspend fun insertTestLog(){
        val test: MutableList<ExerciseLog> = mutableListOf()
        for (i in 1..LocalDate.now().lengthOfMonth()) {
            test += ExerciseLog(0, 1, LocalDate.of(2023,5,i), LocalTime.now(), 10, 11)
            test += ExerciseLog(0, 2, LocalDate.of(2023,5,i), LocalTime.now(), 10, 11)
            test += ExerciseLog(0, 3, LocalDate.of(2023,5,i), LocalTime.now(), 10, 11)
        }
        test.forEach { exercise ->
            exerciseDao.logExercise(exercise)
        }
    }


    suspend fun getLogByDate(date: LocalDate): LiveData<List<ExerciseLog>> {
        return exerciseDao.getLogByDate(date)
    }

    fun getMonthLog(start: LocalDate, end: LocalDate): LiveData<List<ExerciseLog>> {
        return exerciseDao.getMonthLog(start, end)
    }
}