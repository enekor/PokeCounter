package com.example.rachasevo.ui.listado

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rachasevo.R
import com.example.rachasevo.model.Item

class Adaptador(context:Context): RecyclerView.Adapter<Adaptador.ViewHolder>() {

    val contexto = context
    lateinit var items:List<Item>
    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var imagen:ImageView
        lateinit var add:ImageView
        lateinit var substract:ImageView
        lateinit var name:TextView
        lateinit var counter:TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.counter_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Meter los datos desde el array a los items del viewholder")
    }

    override fun getItemCount():Int = items.size
}