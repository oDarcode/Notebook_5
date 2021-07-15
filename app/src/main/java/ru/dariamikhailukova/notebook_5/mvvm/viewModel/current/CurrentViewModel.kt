package ru.dariamikhailukova.notebook_5.mvvm.viewModel.current

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.dariamikhailukova.notebook_5.SingleLiveEvent
import ru.dariamikhailukova.notebook_5.data.Note
import ru.dariamikhailukova.notebook_5.data.NoteRepository
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CurrentViewModel(private val repository: NoteRepository): ViewModel() {

    var id = MutableLiveData<Long>()
    var name = MutableLiveData<String>()
    var text = MutableLiveData<String>()
    var date = MutableLiveData<Date>()

    var dateStr = MutableLiveData<String>()

    fun initAll(note: Note){
        id.value = note.id
        name.value = note.name
        text.value = note.text
        date.value = note.date
        dateStr.value = getDate()

    }

    //попытка сохранения пустой заметки
    val onAttemptSaveEmptyNote = SingleLiveEvent<Unit>()

    //успешное удаление заметки
    val onDeleteSuccess = SingleLiveEvent<Unit>()

    //успешное обновление заметки
    val onUpdateSuccess = SingleLiveEvent<Unit>()

    //успешная отправки заметки
    val onSendSuccess = SingleLiveEvent<Unit>()

    fun updateNote(){
        if (inputCheck()){
            val note = Note(id.value!!.toLong(), name.value.toString(), text.value.toString(), Date())
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateNote(note)
            }
            onUpdateSuccess.call()
        }else{
            Log.d(TAG, "Не удалось обновить заметку")
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

    fun sendNote(){
        if (inputCheck()){
            onSendSuccess.call()
        }else{
            Log.d(TAG, "Не удалось отправить заметку")
            onAttemptSaveEmptyNote.call()
        }
    }

    private fun inputCheck(): Boolean{
        return !(name.value.isNullOrBlank() || text.value.isNullOrBlank())
        //return !(TextUtils.isEmpty(name.value) || TextUtils.isEmpty(text.value))
    }

    private fun getDate(): String {
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return  (timeFormat.format(date.value!!) + "  " + dateFormat.format(date.value!!))
    }

    companion object{
        const val TAG = "CurrentViewModel"
    }

}