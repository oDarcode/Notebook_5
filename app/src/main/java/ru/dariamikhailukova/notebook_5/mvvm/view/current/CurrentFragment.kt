package ru.dariamikhailukova.notebook_5.mvvm.view.current

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.dariamikhailukova.notebook_5.R
import ru.dariamikhailukova.notebook_5.data.Note
import ru.dariamikhailukova.notebook_5.data.NoteDatabase
import ru.dariamikhailukova.notebook_5.data.NoteRepository
import ru.dariamikhailukova.notebook_5.databinding.FragmentCurrentBinding
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.current.CurrentViewModel

class CurrentFragment : Fragment(), CurrentView {
    private lateinit var binding: FragmentCurrentBinding
    private lateinit var mCurrentViewModel: CurrentViewModel

    lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentBinding.inflate(inflater, container, false)

        val noteDao = NoteDatabase.getDatabase(requireContext()).noteDao()
        mCurrentViewModel = CurrentViewModel(NoteRepository(noteDao))


        binding.currentViewModel = mCurrentViewModel
        binding.lifecycleOwner = this

        subscribeToViewModel()

        return binding.root
    }

    private fun subscribeToViewModel(){
        mCurrentViewModel.onAttemptSaveEmptyNote.observe(this){
            Toast.makeText(requireContext(), R.string.fill_all, Toast.LENGTH_SHORT).show()
        }

        mCurrentViewModel.onDeleteSuccess.observe(this){
            activity?.onBackPressed()
            Toast.makeText(requireContext(), R.string.remove, Toast.LENGTH_SHORT).show()
        }

        mCurrentViewModel.onUpdateSuccess.observe(this){
            activity?.onBackPressed()
            Toast.makeText(requireContext(), R.string.update, Toast.LENGTH_SHORT).show()
        }

        mCurrentViewModel.onSendSuccess.observe(this){
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, mCurrentViewModel.name.value)
                putExtra(Intent.EXTRA_TEXT, mCurrentViewModel.text.value)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, ""))
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(NOTE) }?.apply {
            note = this.getParcelable(NOTE)!!
            mCurrentViewModel.initAll(note)
        }
    }

    override fun updateItem() {
        mCurrentViewModel.updateNote()
    }

    override fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)){ _, _->
            mCurrentViewModel.deleteNote()
        }

        builder.setNegativeButton(getString(R.string.no)){ _, _->}
        builder.setTitle(getString(R.string.do_delete))
        builder.setMessage(getString(R.string.are_you_sure))
        builder.create().show()
    }

    override fun sendEmail() {
        mCurrentViewModel.sendNote()
    }


    companion object{
        const val NOTE = "Note"
    }
}