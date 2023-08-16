package com.example.keeptodo.room

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class ContactState(
    val contacts:List<Contact> = emptyList(),
    val date: String =  DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(LocalDate.now()),
    val context:String = "",
    val color: Int = 1,
    val sortType: SortType = SortType.DATE
)
