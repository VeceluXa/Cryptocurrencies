package com.danilovfa.cryptocurrencies.data.local.converters

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateTypeConverter {
    @TypeConverter
    fun fromDate(date: LocalDate?): String {
        return date?.toString() ?: ""
    }

    @TypeConverter
    fun fromString(dateStr: String): LocalDate? {
        return if (dateStr.isNotEmpty()) LocalDate.parse(dateStr) else null
    }
}