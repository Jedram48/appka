package com.example.musicapp.learningModule

data class ParentItem(
    val title : String,
    val image : Int ,
    val childItemList : ArrayList<ChildItem>,
    var isExpandable : Boolean = false
)