package ru.dariamikhailukova.notebook_5.data

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.dariamikhailukova.notebook_5.SingleLiveEvent
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class NoteViewModel(private var repository: NoteRepository): ViewModel() {

    lateinit var name: String
    var id = MutableLiveData<Long>()
    var date = Int

    //успешное сохранение заметки
    val onSaveSuccess: SingleLiveEvent<Unit> = SingleLiveEvent()

    fun addNote(){
        name = "something"

        id.value = 45
        onSaveSuccess.call()
    }

    /*
    var id = MutableLiveData<Long>()
    var name = MutableLiveData<String>()
    var text = MutableLiveData<String>()
    var date = MutableLiveData<Date>()

    var date_str = MutableLiveData<String>()

    fun initAll(note: Note){
        id.value = note.id
        name.value = note.name
        text.value = note.text
        date.value = note.date
        date_str.value = getDate()

    }

    //попытка сохранения пустой заметки
    val onAttemptSaveEmptyNote = SingleLiveEvent<Unit>()

    //успешное сохранение заметки
    val onSaveSuccess = SingleLiveEvent<Unit>()

    //успешное удаление заметки
    val onDeleteSuccess = SingleLiveEvent<Unit>()

    //успешное удаление заметок
    val onAllDeleteSuccess = SingleLiveEvent<Unit>()

    //успешное обновление заметки
    val onUpdateSuccess = SingleLiveEvent<Unit>()

    val readAllData: LiveData<List<Note>> = repository.readAllData

    fun addNote(){
        if (inputCheck()){
            val note = Note(0, name.value.toString(), text.value.toString(), Date())
            viewModelScope.launch(Dispatchers.IO) {
                repository.addNote(note)
            }
            onSaveSuccess.call()
        }else{
            Log.d("TAG", "Не удалось сохранить заметку")
            onAttemptSaveEmptyNote.call()
        }
    }

    fun updateNote(){
        if (inputCheck()){
            val note = Note(id.value!!.toLong(), name.value.toString(), text.value.toString(), Date())
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateNote(note)
            }
            onUpdateSuccess.call()
        }else{
            Log.d("TAG", "Не удалось обновить заметку")
            onAttemptSaveEmptyNote.call()
        }
    }

    fun deleteNote(){
        val note = Note(id.value!!.toLong(), name.value.toString(), text.value.toString(), date.value!!)

        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
        onDeleteSuccess.call()
    }

    fun deleteAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNotes()
        }
        onAllDeleteSuccess.call()
    }

    private fun inputCheck(): Boolean{
        return !(TextUtils.isEmpty(name.value) || TextUtils.isEmpty(text.value))
    }

    private fun getDate(): String {
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return  (timeFormat.format(date.value) + "  " + dateFormat.format(date.value)).toString()
    }
*/
}