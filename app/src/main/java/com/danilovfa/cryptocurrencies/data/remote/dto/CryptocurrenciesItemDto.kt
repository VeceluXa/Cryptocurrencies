package com.danilovfa.cryptocurrencies.data.remote.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CryptocurrenciesItemDto(
    @SerializedName("current_price")
    val currentPrice: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("market_cap")
    val marketCap: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("price_change_24h")
    val priceChange24h: Double,
    @SerializedName("symbol")
    val symbol: String,
)