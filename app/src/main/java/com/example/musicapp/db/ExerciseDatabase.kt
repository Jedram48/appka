package com.example.musicapp.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.musicapp.models.Exercise
import com.example.musicapp.models.ExerciseLog

@Database(entities = [Exercise::class, ExerciseLog::class],
    version = 1,
//    autoMigrations = [AutoMigration(from = 1, to = 2)]
    )
@TypeConverters(DateConverters::class, TimeConverters::class)
abstract class ExerciseDatabase: RoomDatabase(){

    abstract val dao: ExerciseDao

    companion object{
        @Volatile
        private var INSTANCE: ExerciseDatabase? = null

        fun getDatabase(context: Context): ExerciseDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExerciseDatabase::class.java,
                        "exercise-database"
                    ).build()
                }

                return instance
            }
        }
    }


}