package com.danilovfa.cryptocurrencies.domain.usecase

import com.danilovfa.cryptocurrencies.domain.model.User
import com.danilovfa.cryptocurrencies.domain.repository.UserRepository

class GetUserUseCase(
    private val repository: UserRepository
) {
    fun execute(): User {
        TODO()
    }
}