package ru.dariamikhailukova.notebook_5


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import ru.dariamikhailukova.notebook_5.data.Note
import ru.dariamikhailukova.notebook_5.data.NoteRepository
import ru.dariamikhailukova.notebook_5.data.NoteViewModel
import ru.dariamikhailukova.notebook_5.mvvm.view.add.AddFragment
import ru.dariamikhailukova.notebook_5.mvvm.view.current.CurrentFragment
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.add.AddViewModel
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.current.CurrentViewModel
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.list.ListViewModel
import java.security.AccessController.getContext
import java.util.*



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(JUnit4::class)
class ExampleUnitTest {

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: NoteRepository
    private lateinit var viewModel: NoteViewModel

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)
        repository = mock(NoteRepository::class.java)
        viewModel = NoteViewModel(repository)
    }

    @Test
    fun saveNote(){
        viewModel.addNote()
        var successSaved = false
        viewModel.onSaveSuccess.observeForever{
            successSaved = true
        }
        assertTrue(successSaved)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}