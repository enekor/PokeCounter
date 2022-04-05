package com.example.rachasevo.rest

import android.util.Log
import com.example.rachasevo.rest.config.Api
import com.example.rachasevo.rest.config.ApiConfig
import com.example.rachasevo.rest.model.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetImageFromApi {

    val api = ApiConfig.getClient()?.create(Api::class.java)

    fun getPokemon(nombre:String):List<String>{

        val call = api?.getPokemon(nombre)
        lateinit var pokemon:Pokemon

        call?.enqueue(object : Callback<Pokemon> {
            override fun onFailure(call: Call<Pokemon>?, t: Throwable?) {
                Log.v("retrofit", "call failed")
            }

            override fun onResponse(call: Call<Pokemon>?, response: Response<Pokemon>?) {
                pokemon = response?.body()!!
            }
        })

        return setImages(pokemon)
    }

    private fun setImages(pokemon:Pokemon):MutableList<String>{
        val lista = mutableListOf<String>()
        val sprites = pokemon.sprites.other?.home

        sprites?.frontDefault?.let { lista.add(it) }
        sprites?.frontShiny?.let { lista.add(it) }

        return lista
    }
}