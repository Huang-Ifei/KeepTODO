package com.example.keeptodo.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface  ContactDao {
    @Upsert
    suspend fun upsertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contact ORDER BY date ASC")
    fun getContactByDate():Flow<List<Contact>>
}


@Dao
interface  HistoryContactDao {
    @Upsert
    suspend fun upsertContact(contact: HistoryContact)

    @Delete
    suspend fun deleteContact(contact: HistoryContact)

    @Query("SELECT * FROM historycontact ORDER BY date ASC")
    fun getContactByDate():Flow<List<HistoryContact>>
}
