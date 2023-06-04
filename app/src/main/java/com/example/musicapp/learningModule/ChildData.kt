package com.example.musicapp.learningModule

data class ChildData(
    val title: String,
    val logo: Int,
    val desc: String,
    var isExpandable: Boolean = false
)