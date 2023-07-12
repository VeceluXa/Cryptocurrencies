package com.danilovfa.cryptocurrencies.utils

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.danilovfa.cryptocurrencies.R
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0..22)
    }

fun String.toLocalDate(): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    var localDate: LocalDate? = null
    try {
        localDate = LocalDate.parse(this, formatter)
        Log.d(TAG, "Date: $localDate")
    } catch(e: DateTimeParseException) {
        Log.e(TAG, "Failed to parse LocalDate")
    }
    return localDate
}

fun LocalDate?.toFormattedString(): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return this?.format(formatter) ?: ""
}

fun View.snack(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar
        .make(this, messageRes, length)
        .show()
}

fun Fragment.loadImageByUri(image: Uri, container: ImageView) {
    Glide
        .with(this)
        .load(image)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .centerCrop()
        .placeholder(R.drawable.baseline_person_24)
        .into(container)
}

fun Context.showListDialog(@StringRes title: Int, @ArrayRes items: Int, onItemClick: (id: Int) -> Unit) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setItems(items) { _, which -> onItemClick(which) }
        .create()
        .show()
}