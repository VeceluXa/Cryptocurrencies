package com.danilovfa.cryptocurrencies.domain.usecase

import com.danilovfa.cryptocurrencies.domain.model.User
import com.danilovfa.cryptocurrencies.domain.repository.UserRepository

class SaveUserUseCase(
    private val repository: UserRepository
) {
    suspend fun execute(user: User) {
        repository.getUser()
    }
}