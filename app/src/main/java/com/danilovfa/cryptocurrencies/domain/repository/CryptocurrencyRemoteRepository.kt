package com.danilovfa.cryptocurrencies.domain.repository

import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem

interface CryptocurrencyRemoteRepository {
    suspend fun getCryptocurrenciesByName(page: Int): List<CryptocurrencyItem>
    suspend fun getCryptocurrenciesByPrice(page: Int): List<CryptocurrencyItem>
    suspend fun getCryptocurrencyDetails(id: String): CryptocurrencyDetails
}