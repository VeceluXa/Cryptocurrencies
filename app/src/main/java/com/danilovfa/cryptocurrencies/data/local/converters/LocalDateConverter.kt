package com.danilovfa.cryptocurrencies.data.local.converters

import androidx.room.TypeConverter
import com.danilovfa.cryptocurrencies.data.local.model.UserEntity
import com.danilovfa.cryptocurrencies.domain.model.User
import java.time.LocalDate

class LocalDateConverter {
    @TypeConverter
    fun fromDate(date: LocalDate?): String {
        return date?.toString() ?: ""
    }

    @TypeConverter
    fun fromString(dateStr: String): LocalDate? {
        return if (dateStr.isNotEmpty()) LocalDate.parse(dateStr) else null
    }
}