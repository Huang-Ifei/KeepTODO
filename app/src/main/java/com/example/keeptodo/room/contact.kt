package com.example.keeptodo.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Month

@Entity
data class Contact(
    val date: String,
    val context:String,
    val color: Int,
    @PrimaryKey(autoGenerate = true)
    val id:Int =0
)


@Entity
data class HistoryContact(
    val date: String,
    val context:String,
    val color: Int,
    val month: String,
    @PrimaryKey(autoGenerate = true)
    val id:Int =0
)


var editNum = 0

var arrDate =  Array(6){ Array(7) { "" } }
var arrShowColor =  Array(32){ Array(3) { 0 } }