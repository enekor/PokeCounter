package com.example.rachasevo.rest

import android.util.Log
import com.example.rachasevo.Intercambio
import com.example.rachasevo.rest.config.Api
import com.example.rachasevo.rest.config.ApiConfig
import com.example.rachasevo.rest.model.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetImageFromApi {

    val api = ApiConfig.getClient()?.create(Api::class.java)

    fun getPokemon(nombre:String){

        Log.i("retrofit","entrando")
        val call = api?.getPokemon(nombre)
        lateinit var pokemon:Pokemon

        call?.enqueue(object : Callback<Pokemon> {
            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.i("retrofit", "call failed")
            }

            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                Log.i("retrofit","succeed")
                pokemon = response.body()!!

                setLista(pokemon)

            }
        })

    }

    private fun setLista(pokemon: Pokemon) {
        val lista = mutableListOf<String>()
        val sprites = pokemon.sprites.other?.home

        sprites?.front_default?.let { lista.add(it) }
        sprites?.front_shiny?.let { lista.add(it) }

        Intercambio.pokelist = lista
    }
}