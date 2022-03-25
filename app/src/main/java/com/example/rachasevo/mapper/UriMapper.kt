package com.example.rachasevo.mapper

import android.net.Uri

object UriMapper {

    fun uriToString(uri: Uri):String = uri.toString()

    fun stringToUri(string: String):Uri = Uri.parse(string)
}