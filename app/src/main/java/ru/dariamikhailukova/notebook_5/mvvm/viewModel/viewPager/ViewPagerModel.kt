package ru.dariamikhailukova.notebook_5.mvvm.viewModel.viewPager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.dariamikhailukova.notebook_5.data.Note
import ru.dariamikhailukova.notebook_5.data.NoteRepository

class ViewPagerModel(repository: NoteRepository): ViewModel() {
    val readAllData: LiveData<List<Note>> = repository.readAllData
}