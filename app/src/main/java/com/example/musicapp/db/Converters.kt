package com.example.musicapp.db

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

class TimeConverters{
    @TypeConverter
    fun fromTime(time: LocalTime): String {
        return time.toString()
    }

    @TypeConverter
    fun toTime(time: String): LocalTime {
        return LocalTime.parse(time)
    }
}

class DateConverters{
    @TypeConverter
    fun fromDate(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    fun toDate(date: String): LocalDate {
        return LocalDate.parse(date)
    }
}