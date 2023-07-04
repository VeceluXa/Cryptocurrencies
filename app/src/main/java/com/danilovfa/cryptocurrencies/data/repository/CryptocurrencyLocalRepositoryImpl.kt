package com.danilovfa.cryptocurrencies.data.repository

import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.domain.repository.CryptocurrencyLocalRepository

class CryptocurrencyLocalRepositoryImpl: CryptocurrencyLocalRepository {
    override suspend fun getCryptocurrenciesByName(page: Int): List<CryptocurrencyItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getCryptocurrenciesByPrice(page: Int): List<CryptocurrencyItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getCryptocurrencyDetails(id: String): CryptocurrencyDetails {
        TODO("Not yet implemented")
    }
}