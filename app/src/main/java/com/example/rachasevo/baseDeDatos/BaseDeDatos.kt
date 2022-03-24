package com.example.rachasevo.baseDeDatos

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rachasevo.baseDeDatos.dao.ItemDao
import com.example.rachasevo.baseDeDatos.model.Item

@Database(entities = [Item::class], version = 1)
abstract class BaseDeDatos:RoomDatabase(){

    abstract fun itemDao(): ItemDao
}