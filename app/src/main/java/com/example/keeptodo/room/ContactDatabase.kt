package com.example.keeptodo.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Contact::class],
    version = 1
)
abstract class ContactDatabase:RoomDatabase() {

    abstract val dao:ContactDao
}


@Database(
    entities = [HistoryContact::class],
    version = 1
)
abstract class HistoryContactDatabase:RoomDatabase() {

    abstract val dao:HistoryContactDao
}

