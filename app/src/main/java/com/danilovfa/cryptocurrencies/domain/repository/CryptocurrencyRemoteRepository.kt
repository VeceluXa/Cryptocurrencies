package com.danilovfa.cryptocurrencies.domain.repository

import com.danilovfa.cryptocurrencies.domain.model.CryptocurrenciesOrder
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.domain.model.Resource

interface CryptocurrencyRemoteRepository {
    suspend fun fetchCryptocurrencies(page: Int, order: CryptocurrenciesOrder): Resource<List<CryptocurrencyItem>>
    suspend fun fetchCryptocurrencyDetails(id: String): CryptocurrencyDetails
}