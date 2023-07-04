package com.danilovfa.cryptocurrencies.data.remote.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CryptocurrencyDetailsDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: ImageDto,
    @SerializedName("market_data")
    val marketData: MarketDataDto,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
)