package com.example.rachasevo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import com.example.rachasevo.Intercambio
import com.example.rachasevo.MainActivity
import com.example.rachasevo.R

class ViewNewFragmentHolder : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("inicio","hola")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_view_new_fragment_holder)

        Intercambio.fragento?.let { changeFragment(it) }
        Intercambio.fragmentHolder = this
    }

    fun changeFragment(fragment: Fragment){
        val manager = supportFragmentManager
        if(!manager.isDestroyed){
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.newViewContainer, fragment)
            transaction.commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}