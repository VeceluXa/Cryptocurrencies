package com.danilovfa.cryptocurrencies.domain.usecase

import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails
import com.danilovfa.cryptocurrencies.domain.repository.CryptocurrencyLocalRepository

class GetCryptocurrencyDetailsUseCase(
    private val repository: CryptocurrencyLocalRepository
) {
    suspend fun execute(id: String): CryptocurrencyDetails {
        TODO()
    }
}