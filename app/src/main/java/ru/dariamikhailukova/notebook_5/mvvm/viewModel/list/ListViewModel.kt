package ru.dariamikhailukova.notebook_5.mvvm.viewModel.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.dariamikhailukova.notebook_5.SingleLiveEvent
import ru.dariamikhailukova.notebook_5.data.Note
import ru.dariamikhailukova.notebook_5.data.NoteRepository

class ListViewModel(private val repository: NoteRepository): ViewModel() {
    val readAllData: LiveData<List<Note>> = repository.readAllData

    //успешное удаление заметок
    val onAllDeleteSuccess = SingleLiveEvent<Unit>()

    fun deleteAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNotes()
        }
        onAllDeleteSuccess.call()
    }

}