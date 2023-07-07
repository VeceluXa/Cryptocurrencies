package com.danilovfa.cryptocurrencies.data.local.converters

import android.net.Uri
import androidx.room.TypeConverter

class UriTypeConverter {
    @TypeConverter
    fun fromUri(uri: Uri): String {
      return uri.toString()
    }

    @TypeConverter
    fun fromString(uriStr: String): Uri {
        return Uri.parse(uriStr)
    }
}