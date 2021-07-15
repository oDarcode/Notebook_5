package ru.dariamikhailukova.notebook_5.mvvm.view.viewPager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.dariamikhailukova.notebook_5.data.Note
import ru.dariamikhailukova.notebook_5.mvvm.view.current.CurrentFragment
import ru.dariamikhailukova.notebook_5.mvvm.view.current.CurrentFragment.Companion.NOTE

class ViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    private var notes: List<Note> = emptyList()

    override fun getItemCount(): Int = notes.size

    override fun createFragment(position: Int): Fragment {
        val fragment = CurrentFragment()
        fragment.arguments = Bundle().apply {
            putParcelable(NOTE, notes[position])
        }
        return fragment
    }

    fun setData(note: List<Note>){
        this.notes = note
        notifyDataSetChanged()
    }

}