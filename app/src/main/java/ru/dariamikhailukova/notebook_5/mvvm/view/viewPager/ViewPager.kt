package ru.dariamikhailukova.notebook_5.mvvm.view.viewPager

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import ru.dariamikhailukova.notebook_5.R
import ru.dariamikhailukova.notebook_5.data.NoteDatabase
import ru.dariamikhailukova.notebook_5.data.NoteRepository
import ru.dariamikhailukova.notebook_5.databinding.ActivityViewPagerBinding
import ru.dariamikhailukova.notebook_5.mvvm.view.current.CurrentFragment
import ru.dariamikhailukova.notebook_5.mvvm.viewModel.viewPager.ViewPagerModel

class ViewPager : AppCompatActivity() {
    private lateinit var binding: ActivityViewPagerBinding
    private lateinit var mViewPagerModel: ViewPagerModel
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView(){
        adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        mViewPagerModel = ViewPagerModel(NoteRepository(noteDao))

        setSupportActionBar(findViewById(R.id.myToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        mViewPagerModel.readAllData.observe(this, { note ->
            adapter.setData(note)
        })
        //binding.viewPager.adapter?.notifyDataSetChanged();
        setCurrentItem()

    }

    private fun setCurrentItem(){

        //Handler(Looper.getMainLooper()).post { binding.viewPager.setCurrentItem(intent.getIntExtra(NUMBER, 0), false) }

        Handler(Looper.getMainLooper()).postDelayed({
            binding.viewPager.setCurrentItem(intent.getIntExtra(NUMBER, 0), false)
        }, 10)
    }

    //???????????????? ????????
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.view_pager_menu, menu)
        return true
    }

    private fun ViewPager2.findCurrentFragment(fragmentManager: FragmentManager): Fragment? {
        return fragmentManager.findFragmentByTag("f$currentItem")
    }

    //?????????? ???????????????? ????????
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val myFragment: CurrentFragment = binding.viewPager.findCurrentFragment(supportFragmentManager) as CurrentFragment

        if (item.itemId == R.id.menu_delete) {
            myFragment.deleteNote()
        }

        if (item.itemId == R.id.menu_share) {
            myFragment.sendEmail()
        }

        if (item.itemId == R.id.menu_save) {
            myFragment.updateItem()
        }

        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val NUMBER = "Current item"
    }
}