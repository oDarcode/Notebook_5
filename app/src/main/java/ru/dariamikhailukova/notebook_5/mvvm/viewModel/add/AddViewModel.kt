 package ru.dariamikhailukova.notebook_5.mvvm.viewModel.add

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.dariamikhailukova.notebook_5.SingleLiveEvent
import ru.dariamikhailukova.notebook_5.data.Note
import ru.dariamikhailukova.notebook_5.data.NoteDatabase
import ru.dariamikhailukova.notebook_5.data.NoteRepository
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel(private val repository: NoteRepository): ViewModel() {

    var name = MutableLiveData<String>()
    var text = MutableLiveData<String>()

    //var name = String
    //var text = String

    //попытка сохранения пустой заметки
    val onAttemptSaveEmptyNote = SingleLiveEvent<Unit>()

    //успешное сохранение заметки
    val onSaveSuccess = SingleLiveEvent<Unit>()

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

    private fun inputCheck(): Boolean{
        return !(TextUtils.isEmpty(name.value) || TextUtils.isEmpty(text.value))
    }

}