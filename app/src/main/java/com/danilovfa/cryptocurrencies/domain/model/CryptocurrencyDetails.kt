package com.danilovfa.cryptocurrencies.domain.model

data class CryptocurrencyDetails(
    val coinDetails: CryptocurrencyItem,
    val charts: CryptocurrencyChart
)
