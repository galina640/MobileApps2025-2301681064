package com.example.smartnotespro.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY date DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("""
    SELECT * FROM notes
    WHERE title LIKE :searchQuery
    OR content LIKE :searchQuery
""")
    fun searchNotes(
        searchQuery: String
    ): LiveData<List<Note>>
}