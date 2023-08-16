package com.example.keeptodo.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    val date: String,
    val context:String,
    val color: Int,
    @PrimaryKey(autoGenerate = true)
    val id:Int =0
)


var editNum = 0