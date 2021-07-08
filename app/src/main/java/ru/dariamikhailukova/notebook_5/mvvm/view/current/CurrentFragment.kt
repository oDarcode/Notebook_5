package ru.dariamikhailukova.notebook_5.mvvm.view.current

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.dariamikhailukova.notebook_5.R
import ru.dariamikhailukova.notebook_5.data.Note
import ru.dariamikhailukova.notebook_5.databinding.FragmentAddBinding
import ru.dariamikhailukova.notebook_5.databinding.FragmentCurrentBinding
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.add.AddViewModel
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.current.CurrentViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val NOTE = "Note"
class CurrentFragment : Fragment(), CurrentView {
    private lateinit var binding: FragmentCurrentBinding
    private lateinit var mCurrentViewModel: CurrentViewModel

    lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentBinding.inflate(inflater, container, false)
        mCurrentViewModel = ViewModelProvider(this).get(CurrentViewModel::class.java)

        binding.currentViewModel = mCurrentViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(NOTE) }?.apply {
            note = this.getParcelable(NOTE)!!
            mCurrentViewModel.initAll(note)
        }
    }

    override fun updateItem() {
        if (mCurrentViewModel.updateNote()){
            activity?.onBackPressed()
            Toast.makeText(requireContext(), R.string.update, Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(), R.string.fill_all, Toast.LENGTH_SHORT).show()
        }
    }

    override fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)){ _, _->
            mCurrentViewModel.deleteNote()
            activity?.onBackPressed()
            Toast.makeText(requireContext(), R.string.remove, Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton(getString(R.string.no)){ _, _->}
        builder.setTitle(getString(R.string.do_delete))
        builder.setMessage(getString(R.string.are_you_sure))
        builder.create().show()
    }

    override fun sendEmail() {
        if (mCurrentViewModel.inputCheck()){
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, mCurrentViewModel.name.value)
                putExtra(Intent.EXTRA_TEXT, mCurrentViewModel.text.value)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, ""))
        }else{
            Toast.makeText(requireContext(), R.string.fill_all, Toast.LENGTH_SHORT).show()
        }
    }

}