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

/**
 * A simple [Fragment] subclass.
 * Use the [ViewItem.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewItem : Fragment() {

    private lateinit var binding:FragmentViewItemBinding
    private lateinit var item: Item
    private var contador:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = Intercambio.item
        contador = item.contador

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentViewItemBinding.inflate(inflater,container,false)

        initComponents()
        onClick()

        return binding.root
    }


    private fun onClick(){
        binding.addButton.setOnClickListener{
            contador+=binding.howMuchAdd.text.toString().toInt()
            binding.counterView.text = contador.toString()
            Intercambio.item?.contador = contador
        }

        binding.removeButton.setOnClickListener{
            contador-=binding.howMuchAdd.text.toString().toInt()
            binding.counterView.text = contador.toString()
            Intercambio.item?.contador = contador
        }
    }

    private fun initComponents(){
        binding.namePreview.text = item.nombre
        binding.counterView.text = contador.toString()
        binding.addButton.setImageResource(R.drawable.ic_add_circle_green)
        binding.removeButton.setImageResource(R.drawable.ic_substract_circle_red)
        binding.imagenView.setImageResource(R.drawable.ic_launcher_foreground)
    }

    override fun onPause() {
        super.onPause()
        val db = activity?.let { Room.databaseBuilder(it,BaseDeDatos::class.java,"listas").allowMainThreadQueries().build() }!!
        db.itemDao().insertItem(Intercambio.item!!)
    }
}