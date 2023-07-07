package com.danilovfa.cryptocurrencies.domain.usecase

import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.domain.repository.CryptocurrencyLocalRepository

class GetCryptocurrenciesByPriceUseCase(
    private val repository: CryptocurrencyLocalRepository
) {
    suspend fun execute(page: Int): List<CryptocurrencyItem> {
        TODO()
    }
}