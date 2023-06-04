package com.example.musicapp.models

import java.time.LocalDate

data class DailyStat(
    val date: LocalDate,
    val exercises: Int,
    val time: Int
)
