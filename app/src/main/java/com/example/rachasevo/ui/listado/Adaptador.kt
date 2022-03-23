package com.example.rachasevo.ui.listado

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rachasevo.R
import com.example.rachasevo.baseDeDatos.model.Item

class Adaptador(context:Context, lista:List<Item>, counterEdit:EditCounter): RecyclerView.Adapter<Adaptador.ViewHolder>() {

    val contexto = context
    val items = lista
    val clicker = counterEdit

    open class ViewHolder(v: View, counterEdit:EditCounter) : RecyclerView.ViewHolder(v) {

        val imagen:ImageView
        val add:ImageView
        val substract:ImageView
        val name:TextView
        val counter:TextView
        val clicker = counterEdit

        init {
            imagen = v.findViewById(R.id.previewImage)
            add = v.findViewById(R.id.previewAdd)
            substract = v.findViewById(R.id.previewSubstract)
            name = v.findViewById(R.id.ItemNamePreview)
            counter = v.findViewById(R.id.previewCount)
            setImages()
            click()
        }

        private fun setImages(){
            add.setImageResource(R.drawable.ic_add_circle)
            substract.setImageResource(R.drawable.ic_substract_circle)
        }
        private fun click(){
            add.setOnClickListener { clicker.editCounter(absoluteAdapterPosition,'+',counter) }
            substract.setOnClickListener { clicker.editCounter(absoluteAdapterPosition,'-',counter) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.counter_item,parent,false)
        return ViewHolder(view, clicker)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.name.setText(items[i].name)
        holder.counter.setText(items[i].contador.toString())
    }

    override fun getItemCount():Int = items.size
}