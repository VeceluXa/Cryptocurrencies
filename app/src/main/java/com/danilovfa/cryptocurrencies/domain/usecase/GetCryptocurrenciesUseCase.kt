package com.danilovfa.cryptocurrencies.domain.usecase

import com.danilovfa.cryptocurrencies.domain.model.CryptocurrenciesOrder
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.domain.model.ResponseWrapper
import com.danilovfa.cryptocurrencies.domain.repository.CryptocurrencyLocalRepository
import kotlinx.coroutines.flow.Flow

class GetCryptocurrenciesUseCase(
    private val repository: CryptocurrencyLocalRepository
) {
    suspend fun execute(
        page: Int,
        order: CryptocurrenciesOrder
    ): Flow<ResponseWrapper<List<CryptocurrencyItem>>> {
        return repository.getCryptocurrencies(page, order)
    }
}