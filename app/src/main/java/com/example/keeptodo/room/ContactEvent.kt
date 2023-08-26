package com.example.keeptodo.room

import androidx.compose.ui.graphics.Color
import java.time.LocalDate

sealed interface ContactEvent {
    object SaveContact:ContactEvent
    data class SetDate(val date: String):ContactEvent
    data class SetContext(val string: String):ContactEvent
    data class SetColor(val color: Int):ContactEvent
    data class SortContact(val sortType:SortType):ContactEvent
    data class DeleteContact(val contact: Contact):ContactEvent
}

enum class SortType{
    DATE
}

sealed interface HistoryContactEvent {
    object SaveContact:HistoryContactEvent
    data class SetDate(val date: String):HistoryContactEvent
    data class SetMonth(val month: String):HistoryContactEvent
    data class SetContext(val string: String):HistoryContactEvent
    data class SetColor(val color: Int):HistoryContactEvent
    data class SortContact(val sortType:SortType):HistoryContactEvent
    data class DeleteContact(val contact: HistoryContact):HistoryContactEvent
}