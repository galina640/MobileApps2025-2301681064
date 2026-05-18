package com.example.smartnotespro.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.smartnotespro.data.local.Note
import com.example.smartnotespro.data.local.NoteDatabase
import com.example.smartnotespro.data.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application)
    : AndroidViewModel(application) {

    private val repository: NoteRepository

    val allNotes: LiveData<List<Note>>

    init {

        val dao = NoteDatabase
            .getDatabase(application)
            .noteDao()

        repository = NoteRepository(dao)

        allNotes = repository.allNotes
    }

    fun insert(note: Note) =
        viewModelScope.launch {
            repository.insert(note)
        }

    fun update(note: Note) =
        viewModelScope.launch {
            repository.update(note)
        }

    fun delete(note: Note) =
        viewModelScope.launch {
            repository.delete(note)
        }

    fun searchNotes(
        searchQuery: String
    ): LiveData<List<Note>> {

        return repository.searchNotes(searchQuery)
    }
}