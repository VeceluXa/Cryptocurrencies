package com.danilovfa.cryptocurrencies.data.local.converters

import androidx.room.TypeConverter
import com.danilovfa.cryptocurrencies.data.local.model.ChartTimestampEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListChartTimestampTypeConverter {
    @TypeConverter
    fun fromList(list: List<ChartTimestampEntity>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromString(json: String): List<ChartTimestampEntity> {
        val type = object : TypeToken<List<ChartTimestampEntity>>() {}.type
        return Gson().fromJson(json, type)
    }
}