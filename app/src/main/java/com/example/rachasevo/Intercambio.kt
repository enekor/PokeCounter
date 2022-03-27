package com.example.rachasevo

import androidx.fragment.app.Fragment
import com.example.rachasevo.baseDeDatos.model.Item
import com.example.rachasevo.ui.ViewNewFragmentHolder

object Intercambio {

    var item = Item("","",0,false)
    var fragmentHolder:ViewNewFragmentHolder? = null
    var fragento:Fragment? = null
    var imageString = ""
}