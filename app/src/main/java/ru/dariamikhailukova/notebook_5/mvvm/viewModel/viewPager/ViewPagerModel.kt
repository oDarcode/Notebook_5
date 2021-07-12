package ru.dariamikhailukova.notebook_5.mvvm.viewModel.viewPager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.dariamikhailukova.notebook_5.data.Note
import ru.dariamikhailukova.notebook_5.data.NoteDatabase
import ru.dariamikhailukova.notebook_5.data.NoteRepository

class ViewPagerModel(repository: NoteRepository): ViewModel() {
    val readAllData: LiveData<List<Note>> = repository.readAllData
}