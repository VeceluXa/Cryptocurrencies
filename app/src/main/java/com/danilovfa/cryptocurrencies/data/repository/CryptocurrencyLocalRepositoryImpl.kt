package com.danilovfa.cryptocurrencies.data.repository

import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.domain.repository.CryptocurrencyLocalRepository
import com.danilovfa.cryptocurrencies.domain.repository.CryptocurrencyRemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CryptocurrencyLocalRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val remoteRepository: CryptocurrencyRemoteRepository
) : CryptocurrencyLocalRepository {
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