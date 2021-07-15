 package ru.dariamikhailukova.notebook_5.mvvm.viewModel.add

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.dariamikhailukova.notebook_5.SingleLiveEvent
import ru.dariamikhailukova.notebook_5.data.Note
import ru.dariamikhailukova.notebook_5.data.NoteRepository
import java.util.*

class AddViewModel(private val repository: NoteRepository): ViewModel() {

    var name = MutableLiveData<String>()
    var text = MutableLiveData<String>()

    //попытка сохранения пустой заметки
    var onAttemptSaveEmptyNote: SingleLiveEvent<Unit> = SingleLiveEvent()

    //успешное сохранение заметки
    val onSaveSuccess: SingleLiveEvent<Unit> = SingleLiveEvent()

    fun addNote(){
        if (inputCheck()){
            val note = Note(0, name.value.toString(), text.value.toString(), Date())
            viewModelScope.launch(Dispatchers.IO) {
                repository.addNote(note)
            }
            onSaveSuccess.call()
        }else{
            Log.d(TAG, "Не удалось сохранить заметку")
            onAttemptSaveEmptyNote.call()
        }
    }

    fun inputCheck(): Boolean{
        return !(name.value.isNullOrBlank() || text.value.isNullOrBlank())
    }

    companion object{
        const val TAG = "AddViewModel"
    }

}
