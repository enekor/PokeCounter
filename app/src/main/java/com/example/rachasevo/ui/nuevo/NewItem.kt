package com.example.rachasevo.ui.nuevo

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.room.Room
import com.example.rachasevo.Intercambio
import com.example.rachasevo.R
import com.example.rachasevo.baseDeDatos.BaseDeDatos
import com.example.rachasevo.baseDeDatos.model.Item
import com.example.rachasevo.databinding.FragmentNewItemBinding
import com.example.rachasevo.mapper.UriMapper
import com.example.rachasevo.ui.viewItem.ViewItem

lateinit var binding:FragmentNewItemBinding

/**
 * A simple [Fragment] subclass.
 * Use the [NewItem.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewItem : Fragment() {

    private var imageUri:Uri = Uri.EMPTY
    private val REQUEST_CODE = 3

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
        binding.newImagen.setOnClickListener{ openGallery() }
    }

    private fun createItem(){
        if(binding.newNombreItem.text.toString()!=""){
            val item = Item(binding.newNombreItem.text.toString(),getImage(),0, imageUri != Uri.EMPTY)
            val db = activity?.let { it1 -> Room.databaseBuilder(it1,BaseDeDatos::class.java,"listas").allowMainThreadQueries().build() }!!

            db.itemDao().insertItem(item)

            Toast.makeText(activity, "Elemento guardado con exito", Toast.LENGTH_SHORT).show()

            Intercambio.item = item
            Intercambio.fragmentHolder?.changeFragment(ViewItem())
        }else{
            Toast.makeText(activity, "No se puede guardar un elemento sin nombre", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getImage():String =
        if(imageUri !=Uri.EMPTY) UriMapper.uriToString(imageUri) else ""

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && data != null){
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
}