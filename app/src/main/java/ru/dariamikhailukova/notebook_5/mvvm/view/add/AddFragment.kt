package ru.dariamikhailukova.notebook_5.mvvm.view.add

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import ru.dariamikhailukova.notebook_5.R
import ru.dariamikhailukova.notebook_5.data.Note
import ru.dariamikhailukova.notebook_5.data.NoteDatabase
import ru.dariamikhailukova.notebook_5.data.NoteRepository
import ru.dariamikhailukova.notebook_5.databinding.FragmentAddBinding
import ru.dariamikhailukova.notebook_5.http.ApiRequests
import ru.dariamikhailukova.notebook_5.http.CatJson
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.add.AddViewModel
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.current.CurrentViewModel
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.list.ListViewModel
import ru.dariamikhailukova.notebook_5.retrofit.PostRepository
import ru.dariamikhailukova.notebook_5.retrofit.PostViewModel
import ru.dariamikhailukova.notebook_5.retrofit.PostViewModelFactory
import java.util.*

class AddFragment : Fragment(){

    companion object{
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
    private lateinit var binding: FragmentAddBinding
    private lateinit var mAddViewModel: AddViewModel
    private lateinit var mPostViewModel: PostViewModel

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

        val repository = PostRepository()
        val viewModelFactory = PostViewModelFactory(repository)
        mPostViewModel = ViewModelProvider(this, viewModelFactory).get(PostViewModel::class.java)




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
            getCurrentData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getCurrentData(){
        mPostViewModel.getPost()
        mPostViewModel.myResponse.observe(this, { response ->
            if(response.isSuccessful){
                Log.d("Response", response.body()?.id.toString())
                //Log.d("Response", response.body()?.title!!)
                mAddViewModel.name.value = response.body()?.title!!
                mAddViewModel.text.value = response.body()?.body!!
            }else{
                Log.d("Tag", "Error")
            }
        })
    }
}