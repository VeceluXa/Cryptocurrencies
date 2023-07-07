package com.danilovfa.cryptocurrencies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.danilovfa.cryptocurrencies.data.local.converters.ListChartTimestampTypeConverter
import com.danilovfa.cryptocurrencies.data.local.converters.LocalDateConverter
import com.danilovfa.cryptocurrencies.data.local.model.CryptocurrencyChartEntity
import com.danilovfa.cryptocurrencies.data.local.model.CryptocurrencyItemEntity
import com.danilovfa.cryptocurrencies.data.local.model.UserEntity

@Database(
    entities = [
        CryptocurrencyItemEntity::class,
        CryptocurrencyChartEntity::class,
        UserEntity::class
    ],
    version = 1
)
@TypeConverters(ListChartTimestampTypeConverter::class, LocalDateConverter::class)
abstract class CryptocurrencyDatabase : RoomDatabase() {
    abstract val cryptocurrencyDao: CryptocurrencyDao

    companion object {
        const val DATABASE_NAME = "Cryptocurrency"
    }
}