package com.danilovfa.cryptocurrencies.domain.usecase

import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails
import com.danilovfa.cryptocurrencies.domain.model.ResponseWrapper
import com.danilovfa.cryptocurrencies.domain.repository.CryptocurrencyLocalRepository
import kotlinx.coroutines.flow.Flow

class GetCryptocurrencyDetailsUseCase(
    private val repository: CryptocurrencyLocalRepository
) {
    suspend fun execute(id: String): Flow<ResponseWrapper<CryptocurrencyDetails>> {
        return repository.getCryptocurrencyDetails(id)
    }
}