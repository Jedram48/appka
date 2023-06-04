package com.example.musicapp.learningModule

data class ChildItem(
     val title : String,
     val image : Int,
     val desc: String,
     var isExpandable: Boolean = false)