package com.example.musicapp.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.musicapp.models.Exercise
import com.example.musicapp.models.ExerciseLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate


class ExerciseViewModel(application: Application): AndroidViewModel(application) {

    val getAllExerciseData: LiveData<List<Exercise>>
    val getMonthLogData: LiveData<List<ExerciseLog>>
    private val repository: ExerciseRepository

    init {
        val exerciseDao = ExerciseDatabase.getDatabase(application).dao
        repository = ExerciseRepository(exerciseDao)
        getAllExerciseData = repository.getAllExercises()
        getMonthLogData = repository.getMonthLog(LocalDate.now().minusMonths(1), LocalDate.now())
    }


    fun addExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertExercise(exercise)
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteExercise(exercise)
        }
    }

    fun getAllExercises(): LiveData<List<Exercise>> {
        return getAllExerciseData
    }

    fun addDefaultExercises() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertDefaultExercises()
        }
    }

    fun addTestLog() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTestLog()
        }
    }


    fun insertTestLog() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTestLog()
        }
    }
}