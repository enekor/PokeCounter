package com.example.rachasevo.baseDeDatos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(var name:String, var imagen:String, var contador: Int, var internet:Boolean) {
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0
}