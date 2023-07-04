package com.danilovfa.cryptocurrencies.domain.repository

import com.danilovfa.cryptocurrencies.domain.model.User

interface UserRepository {
    fun getUser(): User
    fun saveUser(user: User)
}