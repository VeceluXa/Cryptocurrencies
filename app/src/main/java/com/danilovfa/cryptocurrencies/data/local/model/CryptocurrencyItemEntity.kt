package com.danilovfa.cryptocurrencies.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class CryptocurrencyItemEntity(
    @ColumnInfo(name = "item_id")
    @PrimaryKey(autoGenerate = false)
    val itemId: String,
    val name: String,
    val symbol: String,
    val price: Double,
    @ColumnInfo(name = "market_cap")
    val marketCap: Double,
    @ColumnInfo(name = "image_url")
    val imageUrl: String
)
