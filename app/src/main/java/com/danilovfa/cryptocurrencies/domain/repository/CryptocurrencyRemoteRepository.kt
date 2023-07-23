package com.danilovfa.cryptocurrencies.domain.repository

import com.danilovfa.cryptocurrencies.domain.model.CryptocurrenciesOrder
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyChart
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.domain.model.ResponseWrapper

interface CryptocurrencyRemoteRepository {
    suspend fun fetchCryptocurrencies(page: Int, order: CryptocurrenciesOrder): ResponseWrapper<List<CryptocurrencyItem>>
    suspend fun fetchCryptocurrencyCharts(id: String): ResponseWrapper<CryptocurrencyChart>
}