package ru.dariamikhailukova.notebook_5.mvvm.view.add

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.dariamikhailukova.notebook_5.R
import ru.dariamikhailukova.notebook_5.data.Note
import ru.dariamikhailukova.notebook_5.data.NoteDatabase
import ru.dariamikhailukova.notebook_5.data.NoteRepository
import ru.dariamikhailukova.notebook_5.databinding.FragmentAddBinding
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.add.AddViewModel
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.current.CurrentViewModel
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.list.ListViewModel
import java.util.*


class AddFragment : Fragment(){
    private lateinit var binding: FragmentAddBinding
    private lateinit var mAddViewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        val noteDao = NoteDatabase.getDatabase(requireContext()).noteDao()
        mAddViewModel = AddViewModel(NoteRepository(noteDao))
        setHasOptionsMenu(true)

        binding.addViewModel = mAddViewModel
        binding.lifecycleOwner = this
        subscribeToViewModel()

        return binding.root
    }

    private fun subscribeToViewModel(){
        mAddViewModel.onAttemptSaveEmptyNote.observe(this){
            Toast.makeText(requireContext(), R.string.fill_all, Toast.LENGTH_SHORT).show()
        }

        mAddViewModel.onSaveSuccess.observe(this){
            Toast.makeText(requireContext(), R.string.successfully, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
    }

    //Создание меню
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    //выбор элемента меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_save){
            mAddViewModel.addNote()
        }

        if(item.itemId == R.id.menu_download){
            TODO()
        }
        return super.onOptionsItemSelected(item)
    }

}