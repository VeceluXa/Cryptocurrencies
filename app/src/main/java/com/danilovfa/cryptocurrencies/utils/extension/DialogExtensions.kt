package com.danilovfa.cryptocurrencies.utils.extension

import android.app.AlertDialog
import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import com.danilovfa.cryptocurrencies.R

fun Context.showTextDialog(
    @StringRes title: Int,
    message: String,
    onOkClick: (() -> Unit)? = null
) {
    AlertDialog.Builder(this)
        .setPositiveButton(R.string.ok) { _, _ ->
            if (onOkClick != null) {
                onOkClick()
            }
        }
        .setTitle(title)
        .setMessage(message)
        .create()
        .show()
}

fun Context.showListDialog(
    @StringRes title: Int,
    @ArrayRes items: Int,
    onItemClick: (id: Int) -> Unit
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setItems(items) { _, which -> onItemClick(which) }
        .create()
        .show()
}

fun Context.showRadioListDialog(
    @StringRes title: Int,
    items: Array<String>,
    initialSelectedItemId: Int,
    onItemSelected: (selectedItem: Int) -> Unit
) {
    var selectedItem = initialSelectedItemId
    AlertDialog.Builder(this)
        .setTitle(title)
        .setSingleChoiceItems(items, selectedItem) { _, which ->
            selectedItem = which
        }
        .setPositiveButton(R.string.ok) { _, _ ->
            onItemSelected(selectedItem)
        }
        .setNegativeButton(R.string.cancel, null)
        .create()
        .show()
}