package ru.dariamikhailukova.notebook_5.mvvm.view.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.dariamikhailukova.notebook_5.R
import ru.dariamikhailukova.notebook_5.data.Note

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var noteList = emptyList<Note>()

    //указаить используемые компоненты из макета для отдельного элемента списка
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var name: TextView? = null
        var eachItem: ConstraintLayout? = null

        init{
            name = itemView.findViewById(R.id.noteName)
            eachItem = itemView.findViewById(R.id.rowLayout)
        }
    }

    //задает идентификатор макета для отдельного элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    //количество элементов списка
    override fun getItemCount(): Int {
        return noteList.size
    }


    //связывание используемых текстовых меткок с данными
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = noteList[position]
        holder.name?.text = currentItem.name

        holder.eachItem?.setOnClickListener {
            //val action = ListFragmentDirections.actionListFragmentToShowFragment(currentItem)
            //пока что отключила
            //val action = ListFragmentDirections.actionListFragmentToStartFragment(position)
            //holder.itemView.findNavController().navigate(action)
        }

    }

    //задает элементы
    fun setData(note: List<Note>){
        this.noteList = note
        notifyDataSetChanged()
    }

}