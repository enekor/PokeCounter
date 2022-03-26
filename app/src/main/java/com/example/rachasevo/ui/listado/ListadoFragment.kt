package com.example.rachasevo.ui.listado

import android.app.AlertDialog
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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.rachasevo.Intercambio
import com.example.rachasevo.baseDeDatos.BaseDeDatos
import com.example.rachasevo.baseDeDatos.model.Item
import com.example.rachasevo.databinding.FragmentListadoBinding
import com.example.rachasevo.ui.ViewNewFragmentHolder
import com.example.rachasevo.ui.listado.adaptador.Adaptador
import com.example.rachasevo.ui.listado.adaptador.SwipeGesture
import com.example.rachasevo.ui.nuevo.NewItem

class ListadoFragment : Fragment(),EditCounter {

    private lateinit var binding:FragmentListadoBinding
    private lateinit var items:MutableList<Item>
    private lateinit var db:BaseDeDatos
    private lateinit var adapter:Adaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = activity?.let { Room.databaseBuilder(it,BaseDeDatos::class.java,"listas").allowMainThreadQueries().build() }!!
        items = db.itemDao().getAllItems()
        if(items.isEmpty()) items = mutableListOf(Item("Defecto","",0,false))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListadoBinding.inflate(inflater,container,false)

        setAdapter()

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

    private fun setAdapter(){
        adapter = Adaptador(items,this,this)
        val swipeGesture = object : SwipeGesture(){

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)
                notificateDelete(viewHolder.absoluteAdapterPosition)
            }
        }

        val touchHelper = ItemTouchHelper(swipeGesture)

        binding.listadoRecycler.adapter = adapter
        touchHelper.attachToRecyclerView(binding.listadoRecycler)
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
        setAdapter()
    }

    fun startNewActivity(fragment:Fragment){
        Intercambio.fragento = fragment
        startActivity(Intent(activity,ViewNewFragmentHolder::class.java))
    }

    private fun deleteItem(item:Item){
        db.itemDao().deleteItem(item)
        items = db.itemDao().getAllItems()
        setAdapter()
        Toast.makeText(activity, "Borrado con exito", Toast.LENGTH_SHORT).show()
    }

    private fun notificateDelete(position:Int){

        val dialog = AlertDialog.Builder(activity)
            .setMessage("Desea borrar el contador?")
            .setNegativeButton("No") { view, _ ->
                view.dismiss()
            }
            .setPositiveButton("Si") { view, _ ->
                deleteItem(items[position])
                view.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }
}