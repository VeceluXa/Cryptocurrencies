package com.danilovfa.cryptocurrencies.domain.model

data class CryptocurrencyDetails(
    val id: String,
    val name: String,
    val price: Double,
    val marketCap: Double,
    val priceChange: Double,
    val charts: CryptocurrencyChart,
    val imageUrl: String
)
