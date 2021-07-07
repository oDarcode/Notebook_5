package ru.dariamikhailukova.notebook_5.mvvm.viewModel.current

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
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

class CurrentViewModel(application: Application): AndroidViewModel(application) {
    private val repository: NoteRepository

    var id = MutableLiveData<Long>()
    var name = MutableLiveData<String>()
    var text = MutableLiveData<String>()
    var date = MutableLiveData<Date>()

    var date_str = MutableLiveData<String>()

    init{
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
    }

    fun updateNote(): Boolean{
        if (inputCheck()){
            val note = Note(id.value!!.toLong(), name.value.toString(), text.value.toString(), Date())
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateNote(note)
            }
            return true
        }
        return false
    }

    fun deleteNote(){
        val note = date.value?.let {
            Note(id.value!!.toLong(), name.value.toString(), text.value.toString(),
                it
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (note != null) {
                repository.deleteNote(note)
            }
        }
    }

    fun initAll(note: Note){
        id.value = note.id
        name.value = note.name
        text.value = note.text
        date.value = note.date
        date_str.value = getDate()

    }

    fun inputCheck(): Boolean{
        return !(TextUtils.isEmpty(name.value) || TextUtils.isEmpty(text.value))
    }

    private fun getDate(): String {
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return  (timeFormat.format(date.value) + "  " + dateFormat.format(date.value)).toString()
    }
}