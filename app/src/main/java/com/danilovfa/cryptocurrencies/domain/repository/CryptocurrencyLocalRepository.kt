package com.danilovfa.cryptocurrencies.domain.repository

import com.danilovfa.cryptocurrencies.domain.model.CryptocurrenciesOrder
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface CryptocurrencyLocalRepository {
    suspend fun clearCache()
    suspend fun getCryptocurrencies(page: Int, order: CryptocurrenciesOrder): Flow<Resource<List<CryptocurrencyItem>>>
    suspend fun getCryptocurrencyDetails(id: String): CryptocurrencyDetails
}