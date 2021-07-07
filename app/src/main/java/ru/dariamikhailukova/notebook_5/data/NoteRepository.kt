package ru.dariamikhailukova.notebook_5.data

import androidx.lifecycle.LiveData

//используется паттерн репозиторий
class NoteRepository(private val noteDao: NoteDao) {
    val readAllData: LiveData<List<Note>> = noteDao.readAllData()

    suspend fun addNote(note: Note){
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }

    suspend fun deleteAllNotes(){
        noteDao.deleteAllNotes()
    }

}