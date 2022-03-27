package com.example.rachasevo.ui.nuevo

import android.Manifest.permission.*
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.rachasevo.Intercambio
import com.example.rachasevo.R
import com.example.rachasevo.baseDeDatos.BaseDeDatos
import com.example.rachasevo.baseDeDatos.model.Item
import com.example.rachasevo.databinding.FragmentNewItemBinding
import com.example.rachasevo.mapper.UriMapper
import com.example.rachasevo.ui.nuevo.imagenDesdeInternet.ImagenInternet
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass.
 * Use the [NewItem.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewItem : Fragment() {

    lateinit var binding:FragmentNewItemBinding
    private var imageUri:Uri = Uri.EMPTY
    private val REQUEST_CODE =200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewItemBinding.inflate(inflater,container,false)
        binding.newImagen.setImageResource(R.drawable.ic_image_search)

        onClick()

        return binding.root
    }

    private fun onClick(){
        binding.createNewItem.setOnClickListener{ createItem() }
        binding.newImagen.setOnClickListener{ eleccionDeTipoDeImagen() }
    }

    private fun createItem(){
        if(binding.newNombreItem.text.toString()!="" && (Intercambio.imageString != "" || imageUri != Uri.EMPTY)){
            val item = Item()
            item.imagen = if(imageUri != Uri.EMPTY) UriMapper.uriToString(imageUri) else Intercambio.imageString
            item.nombre = binding.newNombreItem.text.toString()
            item.contador = 0
            item.isInternet = imageUri != Uri.EMPTY

            val db = activity?.let { it1 -> Room.databaseBuilder(it1,BaseDeDatos::class.java,"listas").allowMainThreadQueries().build() }!!

            db.itemDao().insertItem(item)

            Toast.makeText(activity, "Elemento guardado con exito", Toast.LENGTH_SHORT).show()

            activity!!.onBackPressed()
        }else{
            Toast.makeText(activity, "No se puede guardar un elemento sin nombre y/o imagen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getImage():String =
        if(imageUri !=Uri.EMPTY) UriMapper.uriToString(imageUri) else ""

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.setType("image/*")
        startActivityForResult(intent,REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && data != null){
            activity?.contentResolver?.takePersistableUriPermission(
                data.data!!,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            imageUri = data.data!!
            binding.newImagen.setImageURI(imageUri)
        }
    }

    override fun onResume() {
        super.onResume()
        if(imageUri != Uri.EMPTY){
            binding.newImagen.setImageURI(imageUri)
        }else{
            binding.newImagen.setImageResource(R.drawable.ic_image_search)
        }
    }

    private fun checkPermissions():Boolean{
        val read = activity?.let { ContextCompat.checkSelfPermission(it,READ_EXTERNAL_STORAGE) }

        return read == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(){
        if(checkPermissions()){
            Log.i("info","concedido")
            openGallery()
        }else{
            Log.i("info","no concedido")
            request()
        }
    }

    private fun request() =
        activity?.let { ActivityCompat.requestPermissions(it, arrayOf(READ_EXTERNAL_STORAGE),REQUEST_CODE) }

    private fun eleccionDeTipoDeImagen(){
        val dialog = AlertDialog.Builder(activity)
            .setMessage("Como desea escoger la imagen?")
            .setNegativeButton("Local") { view, _ ->
                requestPermission()
                view.dismiss()
            }
            .setPositiveButton("Internet") { view, _ ->
                imagenDesdeInternet()
                view.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }

    private fun imagenDesdeInternet(){
        val imagenInternet = activity?.let { ImagenInternet(it,binding.newImagen) }
        activity?.supportFragmentManager?.let { imagenInternet?.show(it,"internet") }
    }
}