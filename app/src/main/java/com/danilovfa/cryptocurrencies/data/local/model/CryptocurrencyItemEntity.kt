package com.danilovfa.cryptocurrencies.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class CryptocurrencyItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "coin_id")
    val coinId: String,
    val name: String,
    val symbol: String,
    val price: Double,
    @ColumnInfo(name = "market_cap")
    val marketCap: Double,
    @ColumnInfo(name = "price_changed_24h_percentage")
    val priceChanged24hPercentage: Double,
    @ColumnInfo(name = "image_url")
    val imageUrl: String
)
