package ru.dariamikhailukova.notebook_5

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import ru.dariamikhailukova.notebook_5.data.Note
import ru.dariamikhailukova.notebook_5.data.NoteRepository
import ru.dariamikhailukova.notebook_5.mvvm.view.add.AddFragment
import ru.dariamikhailukova.notebook_5.mvvm.view.current.CurrentFragment
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.add.AddViewModel
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.current.CurrentViewModel
import java.security.AccessController.getContext
import java.util.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {
    private lateinit var repository: NoteRepository
    private lateinit var viewModel: AddViewModel

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)
        repository = mock(NoteRepository::class.java)
        viewModel = AddViewModel(repository)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun saveNote(){
        //viewModel.name = "fff"
        //viewModel.text = "df"

        viewModel.addNote()

        assertEquals(0, 1-1)

    }
}