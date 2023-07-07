package com.danilovfa.cryptocurrencies.domain.repository

import com.danilovfa.cryptocurrencies.domain.model.User

interface UserRepository {
    suspend fun getUser(): User
    suspend fun saveUser(user: User)
}