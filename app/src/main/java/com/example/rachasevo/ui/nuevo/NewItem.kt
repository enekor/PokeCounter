package com.example.rachasevo.ui.nuevo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.room.Room
import com.example.rachasevo.Intercambio
import com.example.rachasevo.baseDeDatos.BaseDeDatos
import com.example.rachasevo.baseDeDatos.model.Item
import com.example.rachasevo.databinding.FragmentNewItemBinding
import com.example.rachasevo.ui.viewItem.ViewItem

lateinit var binding:FragmentNewItemBinding

/**
 * A simple [Fragment] subclass.
 * Use the [NewItem.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewItem : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewItemBinding.inflate(inflater,container,false)

        onClick()

        return binding.root
    }

    private fun onClick(){
        binding.createNewItem.setOnClickListener{
            if(binding.newNombreItem.text.toString()!=""){
                val item = Item(binding.newNombreItem.text.toString(),"",0,false)

                val db = activity?.let { it1 -> Room.databaseBuilder(it1,BaseDeDatos::class.java,"listas").allowMainThreadQueries().build() }!!
                db.itemDao().insertItem(item)
                Toast.makeText(activity, "Elemento guardado con exito", Toast.LENGTH_SHORT).show()
                Intercambio.item = item
                Intercambio.fragmentHolder?.changeFragment(ViewItem())
            }else{
                Toast.makeText(activity, "No se puede guardar un elemento sin nombre", Toast.LENGTH_SHORT).show()
            }
        }
    }

}