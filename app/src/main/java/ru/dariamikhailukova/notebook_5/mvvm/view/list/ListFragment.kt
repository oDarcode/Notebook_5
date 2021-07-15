package ru.dariamikhailukova.notebook_5.mvvm.view.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.dariamikhailukova.notebook_5.R
import ru.dariamikhailukova.notebook_5.data.NoteDatabase
import ru.dariamikhailukova.notebook_5.data.NoteRepository
import ru.dariamikhailukova.notebook_5.databinding.FragmentListBinding
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.list.ListViewModel


class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var mListViewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        val noteDao = NoteDatabase.getDatabase(requireContext()).noteDao()
        mListViewModel = ListViewModel(NoteRepository(noteDao))
        setHasOptionsMenu(true)

        val adapter = ListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mListViewModel.readAllData.observe(viewLifecycleOwner, { note ->
            adapter.setData(note)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        subscribeToViewModel()

        return binding.root
    }

    private fun subscribeToViewModel(){
        mListViewModel.onAllDeleteSuccess.observe(this){
            Toast.makeText(requireContext(), R.string.remove_all, Toast.LENGTH_SHORT).show()
        }
    }

    //Создание меню
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    //выбор элемента меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllNotes()
        }

        return super.onOptionsItemSelected(item)
    }

    //удаление всех элеметов бд
    private fun deleteAllNotes() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(R.string.yes){_,_->
            mListViewModel.deleteAllNotes()
        }

        builder.setNegativeButton(R.string.no){_,_->}
        builder.setTitle(getString(R.string.delete_everything))
        builder.setMessage(R.string.are_you_sure)
        builder.create().show()
    }
}