package com.danilovfa.cryptocurrencies.data.remote.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MarketDataDto(
    @SerializedName("current_price")
    val currentPrice: CurrentPriceDto,
    @SerializedName("market_cap")
    val marketCap: MarketCapDto,
    @SerializedName("price_change_24h_in_currency")
    val priceChange24hInCurrency: PriceChange24hInCurrencyDto,
)