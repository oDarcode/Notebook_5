package ru.dariamikhailukova.notebook_5.mvvm.viewModel.add

import android.app.Application
import android.text.TextUtils
import androidx.databinding.Bindable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.dariamikhailukova.notebook_5.data.Note
import ru.dariamikhailukova.notebook_5.data.NoteDatabase
import ru.dariamikhailukova.notebook_5.data.NoteRepository
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel(application: Application): AndroidViewModel(application) {
    private val repository: NoteRepository

    var name = MutableLiveData<String>()
    var text = MutableLiveData<String>()

    init{
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
    }


    fun addNote(): Boolean {
        if (inputCheck()){
            val note = Note(0, name.value.toString(), text.value.toString(), Date())
            viewModelScope.launch(Dispatchers.IO) {
                repository.addNote(note)
            }
            return true
        }
        return false
    }

    private fun inputCheck(): Boolean{
        return !(TextUtils.isEmpty(name.value) || TextUtils.isEmpty(text.value))
    }
}