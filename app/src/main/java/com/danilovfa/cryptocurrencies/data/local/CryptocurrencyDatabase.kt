package com.danilovfa.cryptocurrencies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danilovfa.cryptocurrencies.data.local.converters.ListChartTimestampTypeConverter
import com.danilovfa.cryptocurrencies.data.local.model.CryptocurrencyChartEntity
import com.danilovfa.cryptocurrencies.data.local.model.CryptocurrencyItemEntity

@Database(
    entities = [
        CryptocurrencyItemEntity::class,
        CryptocurrencyChartEntity::class
    ],
    version = 1
)
@TypeConverters(ListChartTimestampTypeConverter::class)
abstract class CryptocurrencyDatabase : RoomDatabase() {
    abstract val cryptocurrencyDao: CryptocurrencyDao

    companion object {
        const val DATABASE_NAME = "Cryptocurrency"
    }
}