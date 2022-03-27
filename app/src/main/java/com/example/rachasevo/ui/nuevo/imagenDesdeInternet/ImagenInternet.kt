package com.example.rachasevo.ui.nuevo.imagenDesdeInternet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.rachasevo.Intercambio
import com.example.rachasevo.databinding.DialogImagenInternetBinding
import com.squareup.picasso.Picasso

class ImagenInternet(contexto:Context, imageView:ImageView):DialogFragment() {

    private val appContext = contexto
    private val imagen = imageView
    private lateinit var binding: DialogImagenInternetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DialogImagenInternetBinding.inflate(inflater,container,false)

        binding.viaAPI.isClickable = false
        binding.button.setOnClickListener{
            if(checkFields()){
                Intercambio.imageString = binding.imageResourceText.text.toString()

                Picasso.get().load(binding.imageResourceText.text.toString()).into(imagen)
                dismiss()
            }else{
                Toast.makeText(appContext, "No se puede dejar vacio el hueco de texto", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun checkFields():Boolean = binding.imageResourceText.text.toString() != ""
}