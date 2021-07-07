package ru.dariamikhailukova.notebook_5.mvvm.view.add

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
import ru.dariamikhailukova.notebook_5.databinding.FragmentAddBinding
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.add.AddViewModel
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
        mAddViewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        setHasOptionsMenu(true)

        binding.addViewModel = mAddViewModel
        binding.lifecycleOwner = this


        /*binding.textNote.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(char: CharSequence?, start: Int, before: Int, count: Int) {
                //mAddViewModel.setText(char.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

        })*/

        return binding.root
    }

    //Создание меню
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    //выбор элемента меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_save){
            if (mAddViewModel.addNote()){
                Toast.makeText(requireContext(), R.string.successfully, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_listFragment)
            }else{
                Toast.makeText(requireContext(), R.string.fill_all, Toast.LENGTH_SHORT).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

}