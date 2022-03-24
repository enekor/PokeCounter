package com.example.rachasevo.baseDeDatos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(@PrimaryKey(autoGenerate = true)val id:Int, var name:String, var imagen:String, var contador: Int, var internet:Boolean) {
}