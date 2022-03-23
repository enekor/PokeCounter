package com.example.rachasevo.ui.listado

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.rachasevo.R
import com.example.rachasevo.databinding.FragmentListadoBinding


class ListadoFragment : Fragment() {

    private lateinit var binding:FragmentListadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListadoBinding.inflate(inflater,container,false)
        return binding.root
    }

    private fun onClick(){
        binding.newItem.setOnClickListener { toaster("hola") }
    }

    private fun toaster(text:String) = Toast.makeText(activity,text,Toast.LENGTH_SHORT).show()
}