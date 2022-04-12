package com.example.rachasevo.ui.nuevo.imagenDesdeInternet

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.DialogFragment
import com.example.rachasevo.Intercambio
import com.example.rachasevo.R
import com.example.rachasevo.databinding.DialogImagenInternetBinding
import com.example.rachasevo.rest.GetImageFromApi
import com.example.rachasevo.rest.config.Api
import com.example.rachasevo.rest.config.ApiConfig
import com.example.rachasevo.rest.model.Pokemon
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImagenInternet(contexto:Context, imageView:ImageView):DialogFragment() {

    private val appContext = contexto
    private val imagen = imageView
    private lateinit var binding: DialogImagenInternetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DialogImagenInternetBinding.inflate(inflater,container,false)

        binding.previewNewImage.isGone = true
        binding.button.isGone = true
        binding.pokemonNormal.isGone = true
        binding.pokemonShiny.isGone = true
        binding.viaLink.isChecked = true

        onClick()

        return binding.root
    }

    private fun checkFields():Boolean = binding.imageResourceText.text.toString() != ""

    private fun onClick(){

        binding.radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener{
            group,id -> binding.imageResourceText.hint = if(id == R.id.viaLink) "URL imagen" else "Nombre del pokemon"
        })

        binding.button.setOnClickListener{
            if(checkFields()){
                Intercambio.imageString = binding.imageResourceText.text.toString()

                Picasso.get().load(binding.imageResourceText.text.toString()).into(imagen)
                dismiss()
            }else{
                Toast.makeText(appContext, "No se puede dejar vacio el hueco de texto", Toast.LENGTH_SHORT).show()
            }
        }

        binding.previewImageButton.setOnClickListener{
            if(binding.imageResourceText.text.toString()!=""){
                if(binding.viaLink.isChecked){
                    Picasso.get().load(binding.imageResourceText.text.toString()).into(binding.previewNewImage)
                    binding.previewNewImage.isGone = !binding.previewNewImage.isGone
                    binding.button.isGone = !binding.button.isGone
                }
                else{
                    GetImageFromApi().getPokemon(binding.imageResourceText.text.toString())
                    Toast.makeText(appContext,"Buscando",Toast.LENGTH_SHORT).show()

                    val handler = Handler()
                    handler.postDelayed({
                        val pokelist = Intercambio.pokelist
                        Log.i("visible","dentro")
                        if(!pokelist.isEmpty()){

                            binding.pokemonNormal.isGone = !binding.pokemonNormal.isGone
                            binding.pokemonShiny.isGone = !binding.pokemonShiny.isGone

                            Log.i("lista", pokelist.size.toString())
                            Picasso.get().load(pokelist[0]).into(binding.pokemonNormal)
                            Picasso.get().load(pokelist[1]).into(binding.pokemonShiny)

                            pokemonOnClick(Intercambio.pokelist)
                        }else{
                            Toast.makeText(appContext,"No se han encontrado resultados",Toast.LENGTH_SHORT).show()
                        }
                    }, 900)

                }
            }else{
                Toast.makeText(appContext,"Campo vacio encontrado",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun pokemonOnClick(imagenes: MutableList<String>) {
        binding.pokemonNormal.setOnClickListener{
            Intercambio.imageString = imagenes[0]
            Picasso.get().load(imagenes[0]).into(imagen)
            dismiss()
        }

        binding.pokemonShiny.setOnClickListener{
            Intercambio.imageString = imagenes[1]
            Picasso.get().load(imagenes[1]).into(imagen)
            dismiss()
        }
    }
}