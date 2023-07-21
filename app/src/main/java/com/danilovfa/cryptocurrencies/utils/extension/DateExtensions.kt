package com.danilovfa.cryptocurrencies.utils.extension

import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

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