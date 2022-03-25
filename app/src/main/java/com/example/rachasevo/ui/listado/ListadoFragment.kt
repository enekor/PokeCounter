package com.example.rachasevo.ui.listado

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.rachasevo.Intercambio
import com.example.rachasevo.baseDeDatos.BaseDeDatos
import com.example.rachasevo.baseDeDatos.model.Item
import com.example.rachasevo.databinding.FragmentListadoBinding
import com.example.rachasevo.ui.ViewNewFragmentHolder
import com.example.rachasevo.ui.nuevo.NewItem

class ListadoFragment : Fragment(),EditCounter {

    private lateinit var binding:FragmentListadoBinding
    private lateinit var items:List<Item>
    private lateinit var db:BaseDeDatos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = activity?.let { Room.databaseBuilder(it,BaseDeDatos::class.java,"listas").allowMainThreadQueries().build() }!!
        items = db.itemDao().getAllItems()
        if(items.isEmpty()) items = listOf(Item("Defecto","",0,false))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListadoBinding.inflate(inflater,container,false)

        activity?.let { setAdapter(it) }
        binding.listadoRecycler.layoutManager = LinearLayoutManager(activity)

        onClick()
        return binding.root
    }

    private fun onClick(){
        binding.newItem.setOnClickListener {
            startNewActivity(NewItem())
            activity?.finish()
        }
    }

    private fun setAdapter(contexto:Context){
        val adaptador = Adaptador(items,this,this)
        binding.listadoRecycler.adapter = adaptador
    }

    override fun editCounter(posicion: Int, ecuacion: Char, texto: TextView) {

        items[posicion].contador =
            if (ecuacion=='+')
                items[posicion].contador+1
            else
                items[posicion].contador-1

        texto.text = items[posicion].contador.toString()
        Log.i("info",items[posicion].contador.toString())

        db.itemDao().updateCounter(items[posicion].contador,items[posicion].id)
    }

    override fun onResume() {
        super.onResume()
        activity?.let { setAdapter(it) }
    }

    fun startNewActivity(fragment:Fragment){
        Intercambio.fragento = fragment
        startActivity(Intent(activity,ViewNewFragmentHolder::class.java))
    }
}