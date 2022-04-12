package com.example.rachasevo.rest.config

import com.example.rachasevo.rest.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("{name}")
    fun getPokemon(@Path("name") nombrePokemon:String): Call<Pokemon>
}