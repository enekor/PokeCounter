package com.example.rachasevo.ui.viewItem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.example.rachasevo.Intercambio
import com.example.rachasevo.R
import com.example.rachasevo.baseDeDatos.BaseDeDatos
import com.example.rachasevo.baseDeDatos.model.Item
import com.example.rachasevo.databinding.FragmentViewItemBinding
import com.example.rachasevo.mapper.UriMapper
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass.
 * Use the [ViewItem.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewItem : Fragment() {

    private lateinit var binding:FragmentViewItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentViewItemBinding.inflate(inflater,container,false)

        initComponents()
        onClick()

        return binding.root
    }


    private fun onClick(){
        binding.addButton.setOnClickListener{
            Intercambio.item.contador = Intercambio.item.contador+binding.howMuchAdd.text.toString().toInt()
            binding.counterView.text = "Contador: ${Intercambio.item.contador.toString()}"
        }

        binding.removeButton.setOnClickListener{
            Intercambio.item.contador = Intercambio.item.contador-binding.howMuchAdd.text.toString().toInt()
            binding.counterView.text = "Contador: ${Intercambio.item.contador.toString()}"
        }
    }

    private fun initComponents(){
        binding.namePreview.text = Intercambio.item.nombre
        binding.counterView.text = "Contador: ${Intercambio.item.contador.toString()}"
        binding.addButton.setImageResource(R.drawable.ic_add_circle_green)
        binding.removeButton.setImageResource(R.drawable.ic_substract_circle_red)

        if(Intercambio.item.imagen == ""){
            binding.imagenView.setImageResource(R.drawable.ic_launcher_foreground)
        }else{
            Picasso.get().load(Intercambio.item.imagen).into(binding.imagenView)
        }

    }

    override fun onPause() {
        super.onPause()
        val db = activity?.let { Room.databaseBuilder(it,BaseDeDatos::class.java,"listas").allowMainThreadQueries().build() }!!
        db.itemDao().updateCounter(Intercambio.item.contador,Intercambio.item.id)
    }
}