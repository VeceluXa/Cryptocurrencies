package com.danilovfa.cryptocurrencies.utils.extension

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0..22)
    }

fun View.snack(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar
        .make(this, messageRes, length)
        .show()
}