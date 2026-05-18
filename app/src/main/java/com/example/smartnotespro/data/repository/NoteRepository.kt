package com.example.smartnotespro.data.repository

import androidx.lifecycle.LiveData
import com.example.smartnotespro.data.local.Note
import com.example.smartnotespro.data.local.NoteDao

class NoteRepository(
    private val noteDao: NoteDao
) {

    val allNotes: LiveData<List<Note>> =
        noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insertNote(note)
    }

    suspend fun update(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun delete(note: Note) {
        noteDao.deleteNote(note)
    }
    fun searchNotes(
        searchQuery: String
    ): LiveData<List<Note>> {

        return noteDao.searchNotes(searchQuery)
    }
}