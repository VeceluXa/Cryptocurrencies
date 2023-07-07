package com.danilovfa.cryptocurrencies.domain.model

data class CryptocurrencyItem(
    val id: String,
    val name: String,
    val symbol: String,
    val price: Double,
    val marketCap: Double,
    val imageUrl: String
)
