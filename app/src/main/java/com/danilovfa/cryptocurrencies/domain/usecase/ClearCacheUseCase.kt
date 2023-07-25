package com.danilovfa.cryptocurrencies.domain.usecase

import com.danilovfa.cryptocurrencies.domain.repository.CryptocurrencyLocalRepository

class ClearCacheUseCase(
    private val repository: CryptocurrencyLocalRepository
) {
    suspend fun execute() {
        repository.clearCache()
    }
}